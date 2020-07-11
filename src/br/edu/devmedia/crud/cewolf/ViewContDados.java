package br.edu.devmedia.crud.cewolf;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;

public class ViewContDados implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, Serializable {

	private static final long serialVersionUID = 2921831668876177819L;
	
	private final static String[] DIAS = {"seg", "ter", "qua", "qui", "sex", "sab"};
	private final static String[] JORNAIS = {"O Povo", "Wall Street", "Folha SP", "O Globo"};

	@Override
	public Object produceDataset(Map<String, Object> arg0) throws DatasetProduceException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset() {
			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				System.out.println("Finalizou");
			}
		};
		
		for (int i = 0; i < JORNAIS.length; i++) {
			int ultY = (int) (Math.random() * 1000 + 2000);
			for (int j = 0; j < DIAS.length; j++) {
				final int y = ultY + (int)(Math.random() * 800 - 100);
				ultY = y;
				dataset.addValue(y, JORNAIS[i], DIAS[j]);
			}
		}
		
		return dataset;
	}

	@Override
	public String getProducerId() {
		return "ABC";
	}

	@Override
	public boolean hasExpired(Map map, Date desde) {
		return (System.currentTimeMillis() - desde.getTime()) > 5000;
	}

	@Override
	public String generateToolTip(CategoryDataset arg0, int nome, int arg2) {
		return JORNAIS[nome];
	}

	@Override
	public String generateLink(Object arg0, int nome, Object arg2) {
		return JORNAIS[nome];
	}

}
