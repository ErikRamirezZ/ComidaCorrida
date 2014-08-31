package com.raze.cocina.web;
import com.raze.cocina.domain.Orden;
import com.raze.cocina.service.Bebida_OrdenService;
import com.raze.cocina.service.EstatusService;
import com.raze.cocina.service.OrdenService;
import com.raze.cocina.service.Platillo_OrdenService;
import com.raze.cocina.service.UsuarioService;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;

@RooWebJson(jsonObject = Orden.class)
@Controller
@RequestMapping("/ordens")
@RooWebScaffold(path = "ordens", formBackingObject = Orden.class)
public class OrdenController {

	@Autowired
    OrdenService ordenService;

	@Autowired
    Bebida_OrdenService bebida_OrdenService;

	@Autowired
    Platillo_OrdenService platillo_OrdenService;

	@Autowired
    UsuarioService usuarioService;

	@Autowired
    EstatusService estatusService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Orden orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orden);
            return "ordens/create";
        }
        uiModel.asMap().clear();
        ordenService.saveOrden(orden);
        return "redirect:/ordens/" + encodeUrlPathSegment(orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Orden());
        return "ordens/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("orden", ordenService.findOrden(id));
        uiModel.addAttribute("itemId", id);
        return "ordens/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("ordens", ordenService.findOrdenEntries(firstResult, sizeNo));
            float nrOfPages = (float) ordenService.countAllOrdens() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("ordens", ordenService.findAllOrdens());
        }
        addDateTimeFormatPatterns(uiModel);
        return "ordens/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Orden orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, orden);
            return "ordens/update";
        }
        uiModel.asMap().clear();
        ordenService.updateOrden(orden);
        return "redirect:/ordens/" + encodeUrlPathSegment(orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ordenService.findOrden(id));
        return "ordens/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Orden orden = ordenService.findOrden(id);
        ordenService.deleteOrden(orden);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/ordens";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("orden_fecha_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Orden orden) {
        uiModel.addAttribute("orden", orden);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("bebida_ordens", bebida_OrdenService.findAllBebida_Ordens());
        uiModel.addAttribute("platillo_ordens", platillo_OrdenService.findAllPlatillo_Ordens());
        uiModel.addAttribute("usuarios", usuarioService.findAllUsuarios());
        uiModel.addAttribute("estatuses", estatusService.findAllEstatuses());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Orden orden = ordenService.findOrden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(orden.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Orden> result = ordenService.findAllOrdens();
        return new ResponseEntity<String>(Orden.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        Orden orden = Orden.fromJsonToOrden(json);
        ordenService.saveOrden(orden);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+orden.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Orden orden: Orden.fromJsonArrayToOrdens(json)) {
            ordenService.saveOrden(orden);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Orden orden = Orden.fromJsonToOrden(json);
        orden.setId(id);
        if (ordenService.updateOrden(orden) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Orden orden = ordenService.findOrden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        ordenService.deleteOrden(orden);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
