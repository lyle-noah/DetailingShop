package com.green3rd.DetailingShop.security;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	// Markdown 텍스트를 HTML로 변환하는 기능
	public String markdown(String markdown) {

		// Markdown 파서를 초기화함.
		Parser parser = Parser.builder().build();

		// Markdown 텍스트를 파싱하여 Node 객체로 변환합니다.
		Node document = parser.parse(markdown);

		// Node 객체를 HTML로 렌더링합니다.
		HtmlRenderer renderer = HtmlRenderer.builder().build();

		// 변환된 HTML을 반환합니다.
		return renderer.render(document);
	}
}