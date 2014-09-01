package forscher.nocket.page.error;

import forscher.nocket.page.ForscherPage;

@SuppressWarnings("serial")
public class TestErrorPage extends ForscherPage {

	public TestErrorPage() {
		throw new RuntimeException("Diese Exception wird mit absicht geworfen um zu testen, wie die Exception behandelt wird. (Nur im Prod mode relevant)");
	}
}
