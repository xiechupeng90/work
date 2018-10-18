import java.awt.CardLayout;

import javax.swing.JPanel;

public class CardPanel extends JPanel {
	private QueryPanel queryPanel;
	private UpdatePanel updatePanel;
	private int userId;

	public CardPanel(int userId) {
		this.userId = userId;
		CardLayout card = new CardLayout();
		setLayout(card);
		// ��ʼ�����������
		updatePanel = new UpdatePanel(BooksManager.UPDATE, this, card);
		queryPanel = new QueryPanel(BooksManager.UPDATE, this, card,
				updatePanel, userId);
		add("query", queryPanel);
		add("update", updatePanel);
		card.show(this, "query");
	}
}
