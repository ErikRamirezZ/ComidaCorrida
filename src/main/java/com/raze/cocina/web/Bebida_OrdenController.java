package com.raze.cocina.web;
import com.raze.cocina.domain.Bebida_Orden;
import com.raze.cocina.service.BebidaService;
import com.raze.cocina.service.Bebida_OrdenService;
import java.io.UnsupportedEncodingException;
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

@RooWebJson(jsonObject = Bebida_Orden.class)
@Controller
@RequestMapping("/bebida_ordens")
@RooWebScaffold(path = "bebida_ordens", formBackingObject = Bebida_Orden.class)
public class Bebida_OrdenController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Bebida_Orden bebida_Orden = bebida_OrdenService.findBebida_Orden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (bebida_Orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(bebida_Orden.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Bebida_Orden> result = bebida_OrdenService.findAllBebida_Ordens();
        return new ResponseEntity<String>(Bebida_Orden.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        Bebida_Orden bebida_Orden = Bebida_Orden.fromJsonToBebida_Orden(json);
        bebida_OrdenService.saveBebida_Orden(bebida_Orden);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+bebida_Orden.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Bebida_Orden bebida_Orden: Bebida_Orden.fromJsonArrayToBebida_Ordens(json)) {
            bebida_OrdenService.saveBebida_Orden(bebida_Orden);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Bebida_Orden bebida_Orden = Bebida_Orden.fromJsonToBebida_Orden(json);
        bebida_Orden.setId(id);
        if (bebida_OrdenService.updateBebida_Orden(bebida_Orden) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Bebida_Orden bebida_Orden = bebida_OrdenService.findBebida_Orden(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (bebida_Orden == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        bebida_OrdenService.deleteBebida_Orden(bebida_Orden);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    Bebida_OrdenService bebida_OrdenService;

	@Autowired
    BebidaService bebidaService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Bebida_Orden bebida_Orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bebida_Orden);
            return "bebida_ordens/create";
        }
        uiModel.asMap().clear();
        bebida_OrdenService.saveBebida_Orden(bebida_Orden);
        return "redirect:/bebida_ordens/" + encodeUrlPathSegment(bebida_Orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Bebida_Orden());
        return "bebida_ordens/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("bebida_orden", bebida_OrdenService.findBebida_Orden(id));
        uiModel.addAttribute("itemId", id);
        return "bebida_ordens/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("bebida_ordens", bebida_OrdenService.findBebida_OrdenEntries(firstResult, sizeNo));
            float nrOfPages = (float) bebida_OrdenService.countAllBebida_Ordens() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("bebida_ordens", bebida_OrdenService.findAllBebida_Ordens());
        }
        return "bebida_ordens/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Bebida_Orden bebida_Orden, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bebida_Orden);
            return "bebida_ordens/update";
        }
        uiModel.asMap().clear();
        bebida_OrdenService.updateBebida_Orden(bebida_Orden);
        return "redirect:/bebida_ordens/" + encodeUrlPathSegment(bebida_Orden.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, bebida_OrdenService.findBebida_Orden(id));
        return "bebida_ordens/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Bebida_Orden bebida_Orden = bebida_OrdenService.findBebida_Orden(id);
        bebida_OrdenService.deleteBebida_Orden(bebida_Orden);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/bebida_ordens";
    }

	void populateEditForm(Model uiModel, Bebida_Orden bebida_Orden) {
        uiModel.addAttribute("bebida_Orden", bebida_Orden);
        uiModel.addAttribute("bebidas", bebidaService.findAllBebidas());
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
