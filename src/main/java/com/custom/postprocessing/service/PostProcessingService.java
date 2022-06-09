package com.custom.postprocessing.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.custom.postprocessing.scheduler.PostProcessingScheduler;

/**
 * @author kumar.charanswain
 *
 */

@Service
public class PostProcessingService {

	Logger logger = LoggerFactory.getLogger(PostProcessingScheduler.class);

	@Autowired
	PostProcessingScheduler postProcessingScheduler;
	
	List<String> pclFileList = new LinkedList<String>();
	
	public String manualSmartComPostProcessing() {
		logger.info("manual pcl generate process using rest api controller");
		return postProcessingScheduler.smartComPostProcessing();
	}

	public String manualArchivePostProcessing() {
		logger.info("archive process using rest api controller");
		return postProcessingScheduler.archivePostProcessing();
	}
}
