package com.raze.cocina.web;
import com.raze.cocina.reference.CategoriaPlatillo;
import com.raze.cocina.service.CategoriaPlatilloService;
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

@RooWebJson(jsonObject = CategoriaPlatillo.class)
@Controller
@RequestMapping("/categoriaplatilloes")
@RooWebScaffold(path = "categoriaplatilloes", formBackingObject = CategoriaPlatillo.class)
public class CategoriaPlatilloController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        CategoriaPlatillo categoriaPlatillo = categoriaPlatilloService.findCategoriaPlatillo(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (categoriaPlatillo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(categoriaPlatillo.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<CategoriaPlatillo> result = categoriaPlatilloService.findAllCategoriaPlatilloes();
        return new ResponseEntity<String>(CategoriaPlatillo.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        CategoriaPlatillo categoriaPlatillo = CategoriaPlatillo.fromJsonToCategoriaPlatillo(json);
        categoriaPlatilloService.saveCategoriaPlatillo(categoriaPlatillo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
        headers.add("Location",uriBuilder.path(a.value()[0]+"/"+categoriaPlatillo.getId().toString()).build().toUriString());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (CategoriaPlatillo categoriaPlatillo: CategoriaPlatillo.fromJsonArrayToCategoriaPlatilloes(json)) {
            categoriaPlatilloService.saveCategoriaPlatillo(categoriaPlatillo);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        CategoriaPlatillo categoriaPlatillo = CategoriaPlatillo.fromJsonToCategoriaPlatillo(json);
        categoriaPlatillo.setId(id);
        if (categoriaPlatilloService.updateCategoriaPlatillo(categoriaPlatillo) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        CategoriaPlatillo categoriaPlatillo = categoriaPlatilloService.findCategoriaPlatillo(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (categoriaPlatillo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        categoriaPlatilloService.deleteCategoriaPlatillo(categoriaPlatillo);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@Autowired
    CategoriaPlatilloService categoriaPlatilloService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CategoriaPlatillo categoriaPlatillo, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, categoriaPlatillo);
            return "categoriaplatilloes/create";
        }
        uiModel.asMap().clear();
        categoriaPlatilloService.saveCategoriaPlatillo(categoriaPlatillo);
        return "redirect:/categoriaplatilloes/" + encodeUrlPathSegment(categoriaPlatillo.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new CategoriaPlatillo());
        return "categoriaplatilloes/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("categoriaplatillo", categoriaPlatilloService.findCategoriaPlatillo(id));
        uiModel.addAttribute("itemId", id);
        return "categoriaplatilloes/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("categoriaplatilloes", categoriaPlatilloService.findCategoriaPlatilloEntries(firstResult, sizeNo));
            float nrOfPages = (float) categoriaPlatilloService.countAllCategoriaPlatilloes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("categoriaplatilloes", categoriaPlatilloService.findAllCategoriaPlatilloes());
        }
        return "categoriaplatilloes/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid CategoriaPlatillo categoriaPlatillo, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, categoriaPlatillo);
            return "categoriaplatilloes/update";
        }
        uiModel.asMap().clear();
        categoriaPlatilloService.updateCategoriaPlatillo(categoriaPlatillo);
        return "redirect:/categoriaplatilloes/" + encodeUrlPathSegment(categoriaPlatillo.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, categoriaPlatilloService.findCategoriaPlatillo(id));
        return "categoriaplatilloes/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        CategoriaPlatillo categoriaPlatillo = categoriaPlatilloService.findCategoriaPlatillo(id);
        categoriaPlatilloService.deleteCategoriaPlatillo(categoriaPlatillo);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/categoriaplatilloes";
    }

	void populateEditForm(Model uiModel, CategoriaPlatillo categoriaPlatillo) {
        uiModel.addAttribute("categoriaPlatillo", categoriaPlatillo);
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
