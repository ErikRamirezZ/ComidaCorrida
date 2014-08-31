package com.raze.cocina.web;
import com.raze.cocina.domain.Platillo_Orden;
import com.raze.cocina.service.AgregadoService;
import com.raze.cocina.service.PlatilloService;
import com.raze.cocina.service.Platillo_OrdenService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@RooWebJson(jsonObject = Platillo_Orden.class)
@Controller
@RequestMapping("/platillo_ordens")
@RooWebScaffold(path = "platillo_ordens", formBackingObject = Platillo_Orden.class)
public class Platillo_OrdenController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Platillo_Orden platillo_Orden = platillo_OrdenService.findPlatillo_Orden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (platillo_Orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(platillo_Orden.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Platillo_Orden> result = platillo_OrdenService.findAllPlatillo_Ordens();
        return new ResponseEntity<String>(Platillo_Orden.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        Platillo_Orden platillo_Orden = Platillo_Orden.fromJsonToPlatillo_Orden(json);
        platillo_OrdenService.savePlatillo_Orden(platillo_Orden);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+platillo_Orden.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Platillo_Orden platillo_Orden: Platillo_Orden.fromJsonArrayToPlatillo_Ordens(json)) {
            platillo_OrdenService.savePlatillo_Orden(platillo_Orden);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Platillo_Orden platillo_Orden = Platillo_Orden.fromJsonToPlatillo_Orden(json);
        platillo_Orden.setId(id);
        if (platillo_OrdenService.updatePlatillo_Orden(platillo_Orden) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Platillo_Orden platillo_Orden = platillo_OrdenService.findPlatillo_Orden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (platillo_Orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        platillo_OrdenService.deletePlatillo_Orden(platillo_Orden);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    Platillo_OrdenService platillo_OrdenService;

	@Autowired
    AgregadoService agregadoService;

	@Autowired
    PlatilloService platilloService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Platillo_Orden platillo_Orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, platillo_Orden);
            return "platillo_ordens/create";
        }
        uiModel.asMap().clear();
        platillo_OrdenService.savePlatillo_Orden(platillo_Orden);
        return "redirect:/platillo_ordens/" + encodeUrlPathSegment(platillo_Orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Platillo_Orden());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (platilloService.countAllPlatilloes() == 0) {
            dependencies.add(new String[] { "platillo", "platilloes" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "platillo_ordens/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("platillo_orden", platillo_OrdenService.findPlatillo_Orden(id));
        uiModel.addAttribute("itemId", id);
        return "platillo_ordens/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("platillo_ordens", platillo_OrdenService.findPlatillo_OrdenEntries(firstResult, sizeNo));
            float nrOfPages = (float) platillo_OrdenService.countAllPlatillo_Ordens() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("platillo_ordens", platillo_OrdenService.findAllPlatillo_Ordens());
        }
        return "platillo_ordens/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Platillo_Orden platillo_Orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, platillo_Orden);
            return "platillo_ordens/update";
        }
        uiModel.asMap().clear();
        platillo_OrdenService.updatePlatillo_Orden(platillo_Orden);
        return "redirect:/platillo_ordens/" + encodeUrlPathSegment(platillo_Orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, platillo_OrdenService.findPlatillo_Orden(id));
        return "platillo_ordens/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Platillo_Orden platillo_Orden = platillo_OrdenService.findPlatillo_Orden(id);
        platillo_OrdenService.deletePlatillo_Orden(platillo_Orden);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/platillo_ordens";
    }

	void populateEditForm(Model uiModel, Platillo_Orden platillo_Orden) {
        uiModel.addAttribute("platillo_Orden", platillo_Orden);
        uiModel.addAttribute("agregadoes", agregadoService.findAllAgregadoes());
        uiModel.addAttribute("platilloes", platilloService.findAllPlatilloes());
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
}
