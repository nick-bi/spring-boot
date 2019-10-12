package com.nickbi.txlcn.appone.controller;

import com.nickbi.txlcn.appone.service.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.nickbi.txlcn.appone.controller
 * description <p/>
 *
 * @author wzfc-001
 * create on 2019-10-12 10:32
 */
@RestController
@RequestMapping("/one")
public class OneController {
	@Autowired
	private OneService oneService;

	@RequestMapping("/save")
	public String save(String name) throws Exception {
		oneService.saveInfo(name);
		return "one-service-ok";
	}
}
