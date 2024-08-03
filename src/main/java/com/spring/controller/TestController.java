package com.spring.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.model.FileModel;
import com.spring.model.Testbean;

@Controller
public class TestController {

	
	@GetMapping("/home")
	public String show() {
		return "index";
	}
	@RequestMapping(value ="/show",method = RequestMethod.GET)
	public String showMessage(Model m) {
	m.addAttribute("msg","Spring MVC");
		return "showmessage";
	}
	@GetMapping("/showuserform")
	public ModelAndView addUserForm() {
		Testbean bean=new Testbean();
		bean.setId(2);
		String[]frameworks= {"SpringBook"};
		bean.setFrameworks(frameworks);
		ModelAndView mv=new ModelAndView("adduser","obj",new Testbean());
		return mv;
		
	} 
	@PostMapping("/adduser")
	public String addUser (@Validated@ModelAttribute("obj")Testbean bean,BindingResult br,Model m) {
		if(br.hasErrors()) {
			m.addAttribute("obj",bean);
			return "adduser";
		}
		m.addAttribute("obj",bean); 
		return "showmessage";
	}
	@ModelAttribute("frameworks")
	public List<String>addFrameworks(){
		
		List<String>lst=new ArrayList<String>();
				lst.add("SpringBoot");
		lst.add("Bootstrap");
		lst.add("JPA");
				
			return lst;	
				
	}
	@GetMapping("/showfileupload")
	public ModelAndView showFileUpload() {
		return new ModelAndView("fileupload","obj",new FileModel());
	}
	@PostMapping("/uploadfile")
	public String uploadfile(@ModelAttribute("obj")FileModel file,Model model) throws IOException {
		MultipartFile multipartFile = file.getFile();
		String uploadPath="C:\\Users\\Waddy Phone\\eclipse-workspace\\JWD55_Maven\\src\\main\\webapp\\temp\\";
		
		

		// Now do something with file...
		FileCopyUtils.copy(file.getFile().getBytes(), new
		File(uploadPath+file.getFile().getOriginalFilename()));

		String fileName =multipartFile.getOriginalFilename();
		model.addAttribute("fileName", fileName); 
		return"success";
	}
	
}
