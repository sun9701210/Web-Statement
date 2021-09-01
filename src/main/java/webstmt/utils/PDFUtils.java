package webstmt.utils;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;

public class PDFUtils
{
	private static final Logger log = LoggerFactory.getLogger(PDFUtils.class);
	private static final int pageWidth = 1000;
	
	public ByteArrayOutputStream html2Pdf(String html) 
	{
		ConverterProperties props = new ConverterProperties();
		
		DefaultFontProvider fontProvider = new DefaultFontProvider(true, true, true);
		
		props.setFontProvider(fontProvider);
		
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		
		PdfWriter writer = new PdfWriter(bao);
		
		PdfDocument pdf = new PdfDocument(writer);
		
		pdf.setDefaultPageSize(new PageSize(pageWidth, 14400));
		
		Document document = null;
		
		try
		{
			document = HtmlConverter.convertToDocument(html, pdf, props);
		}
		catch(IllegalArgumentException e)
		{
			log.error("Error occurs during PDF converting. Maybe it's fine. Please enable debug to see more details.");
//			log.debug("PDF Converting Error", e);
		}
		
		EndPosition endPosition = new EndPosition();
		LineSeparator separator = new LineSeparator(endPosition);
		document.add(separator);
		document.getRenderer().close();
		
		PdfPage page = pdf.getPage(1);
		
		float y = endPosition.getY() - 36;
		
		page.setMediaBox(new Rectangle(0, y, pageWidth, 14400-y));

		document.close();
		
		return bao;
	}
	
	
	class EndPosition implements ILineDrawer {

		protected float y;
		
		public float getY()
		{
			return y;
		}
		
		@Override
		public void draw(PdfCanvas canvas, Rectangle rect) {
			this.y = rect.getY();
		}

		@Override
		public Color getColor() {
			return null;
		}

		@Override
		public float getLineWidth() {
			return 0;
		}

		@Override
		public void setColor(Color color) {
			
		}

		@Override
		public void setLineWidth(float lineWidth) {
			
		}
		
		
	}

}
