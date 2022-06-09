package com.custom.postprocessing;

import static com.custom.postprocessing.constant.PostProcessingConstant.APPLICATION_PROPERTY_DIRECTORY;
import static com.custom.postprocessing.constant.PostProcessingConstant.PROPERTY_FILE_NAME;
import static com.custom.postprocessing.constant.PostProcessingConstant.ROOT_DIRECTORY;
import static com.custom.postprocessing.constant.PostProcessingConstant.ACCOUNT_KEY_VALUE;
import static com.custom.postprocessing.constant.PostProcessingConstant.CONTANIER_NAME;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.custom.postprocessing.scheduler.PostProcessingScheduler;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * @author kumar.charanswain
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
public class PostProcessingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(PostProcessingApplication.class).sources(PostProcessingApplication.class)
				.properties(getProperties()).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(PostProcessingApplication.class).properties(getProperties());
	}

	static Properties getProperties() {
		Properties props = new Properties();
		try {
			PostProcessingScheduler postProcessingScheduler = new PostProcessingScheduler();
			CloudBlobContainer container = containerInfo();
			CloudBlobDirectory transitDirectory = postProcessingScheduler.getDirectoryName(container, ROOT_DIRECTORY,
					APPLICATION_PROPERTY_DIRECTORY);
			CloudBlockBlob blob = transitDirectory.getBlockBlobReference(PROPERTY_FILE_NAME);
			String propertiesFiles[] = blob.getName().split("/");
			String propertyFileName = propertiesFiles[propertiesFiles.length - 1];
			File sourceFile = new File(propertyFileName);
			blob.downloadToFile(sourceFile.getAbsolutePath());
			props.put("spring.config.location", propertyFileName);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return props;
	}

	public static CloudBlobContainer containerInfo() {
		CloudBlobContainer container = null;
		try {
			CloudStorageAccount account = CloudStorageAccount.parse(ACCOUNT_KEY_VALUE);
			CloudBlobClient serviceClient = account.createCloudBlobClient();
			container = serviceClient.getContainerReference(CONTANIER_NAME);
		} catch (Exception exception) {
			exception.getMessage();
		}
		return container;
	}

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
	}
}
