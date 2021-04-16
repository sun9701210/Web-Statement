package webstmt.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class HtmlUtil implements ApplicationContextAware
{
	private static final Logger log = LoggerFactory.getLogger(HtmlUtil.class);
	private static ApplicationContext appContext;
	
	public static List<String> extractAllImages(String rawHtml)
	{
		List<String> imgList = new ArrayList<>();
		String[] imgTags = rawHtml.split("<img");
		
		for (String imgTag : imgTags) 
		{
			String srcStartText="src=\"";
			
			int imgSrcAttrStartIndex = imgTag.indexOf(srcStartText);
			
			if(imgSrcAttrStartIndex<0) continue;
			
			int imgSrcAttrEndIndex = imgTag.indexOf("\"", imgSrcAttrStartIndex+srcStartText.length());

			String srcAttrValue = imgTag.substring(imgSrcAttrStartIndex+srcStartText.length(), imgSrcAttrEndIndex);
			imgList.add(srcAttrValue);
		}
		
		return imgList;
	}
	
	public String removeHtmlSegment(String rawHtml, String segmentPattern)
	{
		int prefixEndIndex = rawHtml.indexOf(segmentPattern);
		if(prefixEndIndex<0)
		{
			return rawHtml;
		}
		else
		{
			String prefixHtml = rawHtml.substring(0, prefixEndIndex);
			
			String postfixHtml = rawHtml.substring(prefixEndIndex+segmentPattern.length());
			
			String removedHtml = prefixHtml + postfixHtml;
			
			return removeHtmlSegment(removedHtml, segmentPattern);
		}
	}
	
	public String wrapContainer(String rawHtml)
	{
		Resource resource =  appContext.getResource("classpath:static/css/ck-inline-itext.css");
		String css = "";
		
		log.info(css);
		
		try 
		{
			css = Files.contentOf(resource.getFile(), Charset.forName("UTF-8"));
		}
		catch (IOException e) 
		{
			log.error("Fail to read css file from resources.", e);
		}
		
		StringBuilder wrapper = new StringBuilder();
		
		wrapper.append("<html><head><style>");
		wrapper.append(css);
		wrapper.append("</style></head><body><div id=\"container\" class=\"ck ck-content ck-read-only col-md-12 border border-success\">");
		wrapper.append(rawHtml);
		wrapper.append("</div></body></html>");
		
		return wrapper.toString();
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		appContext = context;
	}
}
