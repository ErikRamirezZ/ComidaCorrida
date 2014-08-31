package com.raze.cocina.web;
import com.raze.cocina.reference.CategoriaBebida;
import com.raze.cocina.service.CategoriaBebidaService;
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

@RooWebJson(jsonObject = CategoriaBebida.class)
@Controller
@RequestMapping("/categoriabebidas")
@RooWebScaffold(path = "categoriabebidas", formBackingObject = CategoriaBebida.class)
public class CategoriaBebidaController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        CategoriaBebida categoriaBebida = categoriaBebidaService.findCategoriaBebida(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (categoriaBebida == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(categoriaBebida.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CategoriaBebida> result = categoriaBebidaService.findAllCategoriaBebidas();
        return new ResponseEntity<String>(CategoriaBebida.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        CategoriaBebida categoriaBebida = CategoriaBebida.fromJsonToCategoriaBebida(json);
        categoriaBebidaService.saveCategoriaBebida(categoriaBebida);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+categoriaBebida.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CategoriaBebida categoriaBebida: CategoriaBebida.fromJsonArrayToCategoriaBebidas(json)) {
            categoriaBebidaService.saveCategoriaBebida(categoriaBebida);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CategoriaBebida categoriaBebida = CategoriaBebida.fromJsonToCategoriaBebida(json);
        categoriaBebida.setId(id);
        if (categoriaBebidaService.updateCategoriaBebida(categoriaBebida) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        CategoriaBebida categoriaBebida = categoriaBebidaService.findCategoriaBebida(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (categoriaBebida == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        categoriaBebidaService.deleteCategoriaBebida(categoriaBebida);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    CategoriaBebidaService categoriaBebidaService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CategoriaBebida categoriaBebida, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, categoriaBebida);
            return "categoriabebidas/create";
        }
        uiModel.asMap().clear();
        categoriaBebidaService.saveCategoriaBebida(categoriaBebida);
        return "redirect:/categoriabebidas/" + encodeUrlPathSegment(categoriaBebida.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CategoriaBebida());
        return "categoriabebidas/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("categoriabebida", categoriaBebidaService.findCategoriaBebida(id));
        uiModel.addAttribute("itemId", id);
        return "categoriabebidas/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("categoriabebidas", categoriaBebidaService.findCategoriaBebidaEntries(firstResult, sizeNo));
            float nrOfPages = (float) categoriaBebidaService.countAllCategoriaBebidas() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("categoriabebidas", categoriaBebidaService.findAllCategoriaBebidas());
        }
        return "categoriabebidas/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CategoriaBebida categoriaBebida, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, categoriaBebida);
            return "categoriabebidas/update";
        }
        uiModel.asMap().clear();
        categoriaBebidaService.updateCategoriaBebida(categoriaBebida);
        return "redirect:/categoriabebidas/" + encodeUrlPathSegment(categoriaBebida.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, categoriaBebidaService.findCategoriaBebida(id));
        return "categoriabebidas/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CategoriaBebida categoriaBebida = categoriaBebidaService.findCategoriaBebida(id);
        categoriaBebidaService.deleteCategoriaBebida(categoriaBebida);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/categoriabebidas";
    }

	void populateEditForm(Model uiModel, CategoriaBebida categoriaBebida) {
        uiModel.addAttribute("categoriaBebida", categoriaBebida);
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
