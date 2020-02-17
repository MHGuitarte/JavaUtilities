/**
 * 
 */
package base;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author usuario
 *
 */
public class Paginator extends SimpleTagSupport {
	JspWriter out;

	int pageSize = 5; // default pageValue
	int pageNumber = 1; // the page the user is viewing

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void doTag() throws JspException, IOException {
		out = getJspContext().getOut();
		pageNumber = (getJspContext().findAttribute("page-number") != null)
				? (int) getJspContext().findAttribute("page-number")
				: 1; // if 'page-number' exists, returns its value; otherwise return 1 (st page)
		
		
	}

}
