package com.custom.postprocessing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custom.postprocessing.scheduler.PostProcessingScheduler;
import com.custom.postprocessing.service.PostProcessingService;
/**
 * @author kumar.charanswain
 *
 */

@RestController
public class PostProcessingController {

    @Autowired
    private PostProcessingService postProcessingService;

    @GetMapping(path = "/pclgenerate")
    public String manualPostProcessBatch() {
        return postProcessingService.manualSmartComPostProcessing();
    }
    
    @GetMapping(path = "/manualarchive")
    public String manualManualArchive() {
        return postProcessingService.manualArchivePostProcessing();
    }
    
    @GetMapping(path = "/welcome")
    public String welcome() {
        return "welcome to azure";
    }
}