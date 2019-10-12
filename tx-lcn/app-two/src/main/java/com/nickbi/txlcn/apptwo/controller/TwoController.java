package com.nickbi.txlcn.apptwo.controller;

import com.nickbi.txlcn.apptwo.service.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.nickbi.txlcn.apptwo.controller
 * description <p/>
 *
 * @author wzfc-001
 * create on 2019-10-12 10:23
 */
@RestController
public class TwoController {

	@Autowired
	private TwoService twoService;

	@RequestMapping("/two")
	public boolean two(String name) {
		return twoService.saveInfo(name);
	}
}
