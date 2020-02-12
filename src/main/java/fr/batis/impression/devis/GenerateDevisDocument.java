package main.java.fr.batis.impression.devis;

/**
 * 
 */

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.scene.control.Alert;
import main.java.fr.batis.components.common.BatisUtils;
import main.java.fr.batis.components.common.ImageUtils;
import main.java.fr.batis.entites.Chantier;
import main.java.fr.batis.entites.Devis;
import main.java.fr.batis.entites.Materiel;
import main.java.fr.batis.entites.PhaseConstruction;
import main.java.fr.batis.entites.Signature;
import main.java.fr.batis.service.ParametresService;

/**
 * @author tijos
 *
 */
public class GenerateDevisDocument {
	private Devis currentDevis;

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font nomalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);// ,Font.NORMAL,BaseColor.RED);
	// private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
	// Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private ImageUtils imageUtils;
	private static ParametresService parametresService = new ParametresService();
	private static final Logger LOGGER = LogManager.getLogger(GenerateDevisDocument.class);

	public GenerateDevisDocument(Devis devis) {
		this.currentDevis = devis;
	}

	/**
	 * 
	 * @param devis
	 */
	public void generate(Devis devis) {

		try {
			final String file = "c:/temp/" + devis.getNom() + ".pdf";
			Document document = new Document();

			try {
				PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(file)));
				Rectangle rect = new Rectangle(10, 10, 550, 800);
				writer.setBoxSize("art", rect);
				HeaderFooterPageEvent event = new HeaderFooterPageEvent();
				writer.setPageEvent(event);

				document.open();
				addMetaData(document, devis);
				addTitlePage(document, devis);

				addContent(document, devis);
				document.close();

				openGeneretedFile(file);

			} catch (Exception e) {

				BatisUtils.showAlert(Alert.AlertType.ERROR, null, "Erreur!",
						"Le fichier pdf est déjà ouvert,fermez d'abord ! ");

			}

		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Error : " + e.getCause());

			}

		}

	}

	/**
	 * 
	 * @param fichier
	 */
	private void openGeneretedFile(String fichier) {
		try {

			File pdfFile = new File(fichier);
			if (pdfFile.exists()) {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				}

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param document
	 * @param devis
	 */
	private void addMetaData(Document document, Devis devis) {
		document.addTitle("Devis");
		document.addAuthor("BATIS INC.");
		document.addCreator("BATIS INC.");
	}

	private void addTitlePage(Document document, Devis devis) throws DocumentException {
		imageUtils = new ImageUtils();
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 3);
		PhaseConstruction phase = devis.getPhase();

		Chantier chantier = phase.getChantier();
		Paragraph title = new Paragraph("BATIS INC. ", catFont);
		title.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(title, 2);
		preface.add(title);

		Paragraph page = new Paragraph("CHANTIER DE CONSTRUCTION " + chantier.getNomProjet());
		page.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(page, 1);
		preface.add(page);

		Paragraph descriptChantierPar = new Paragraph(chantier.getDescription());
		descriptChantierPar.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(descriptChantierPar, 1);
		preface.add(descriptChantierPar);

		Paragraph phasePar = new Paragraph("DEVIS POUR LA PHASE " + phase.getNom());
		phasePar.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(phasePar, 1);
		preface.add(phasePar);

		PdfPTable header = new PdfPTable(1);
		PdfPCell descrptTitle = new PdfPCell();
		descrptTitle.setPaddingBottom(15);
		descrptTitle.setPaddingLeft(10);
		descrptTitle.setBorder(Rectangle.BOTTOM);

		descrptTitle.addElement(new Phrase("DESCRIPTION ", new Font(Font.FontFamily.TIMES_ROMAN, 12)));

		header.addCell(descrptTitle);
		Paragraph devisPar = new Paragraph();
		devisPar.setAlignment(Element.ALIGN_CENTER);
		// addEmptyLine(devisPar, 1);
		devisPar.add(header);

		Paragraph descPar = new Paragraph(phase.getDescription(), nomalFont);
		descPar.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(descPar, 1);
		preface.add(devisPar);

		preface.add(descPar);

		addEmptyLine(preface, 1);

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private static void addContent(Document document, Devis devis) throws DocumentException {

		Paragraph page = new Paragraph();
		addEmptyLine(page, 3);

		Anchor anchor = new Anchor("Liste des matériaux à acheter", smallBold);
		anchor.setName("DEVIS " + devis.getDescription());
		// Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph paragraph = new Paragraph(anchor);
		addEmptyLine(paragraph, 1);
		page.add(paragraph);

		// add a table

		Paragraph listeMat = new Paragraph("", nomalFont);
		createTable(listeMat, devis);
		page.add(listeMat);
		addEmptyLine(listeMat, 2);

		// signature

		Anchor signature = new Anchor("Signature", smallBold);
		signature.setName("Signature ");
		Paragraph paragraphe = new Paragraph(signature);
		addEmptyLine(paragraphe, 1);
		addSignature(paragraphe, devis);
		page.add(paragraphe);
		document.add(page);

	}

	private static void addSignature(Paragraph paragraphe, Devis devis) {
		Signature signature = devis.getSignature();

		// addEmptyLine(paragraphe, 1);
		if (signature != null) {
			// parapgraph.add(new Paragraph("Lu et approuvé"));
			paragraphe.add(new Paragraph(signature.getNom() + " " + signature.getPrenom(), nomalFont));
			paragraphe.add(new Paragraph(signature.getFonction(), nomalFont));
			// addEmptyLine(paragraphe, 1);
			paragraphe.add(new Paragraph("Téléphone : " + signature.getTelephone(), nomalFont));
			// paragraphe.add(new Paragraph(signature.getTelephone(),nomalFont));
			// addEmptyLine(paragraphe, 1);
			paragraphe.add(new Paragraph("Email : " + signature.getEmail(), nomalFont));
			// paragraphe.add(new Paragraph(signature.getEmail(),nomalFont));
		}

		paragraphe.add(new Paragraph("Lu et approuvé", nomalFont));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");

		String date = simpleDateFormat.format(new Date());
		paragraphe.add(new Paragraph("Signé le " + date, nomalFont));
	}

	private static void createTable(Paragraph parapgraph, Devis devis) throws BadElementException {
		PdfPTable table = new PdfPTable(7);

		try {
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 250, 180, 150, 120, 200, 180, 100 });
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PdfPCell nom = new PdfPCell(new Phrase("Nom", smallBold));
		nom.setPadding(8);
		nom.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(nom);

		PdfPCell qlte = new PdfPCell(new Phrase("Qualité", smallBold));
		qlte.setPadding(8);
		qlte.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(qlte);

		PdfPCell qte = new PdfPCell(new Phrase("Quantité", smallBold));
		qte.setPadding(8);
		qte.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(qte);

		PdfPCell unite = new PdfPCell(new Phrase("Unité", smallBold));
		unite.setPadding(8);
		unite.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(unite);

		PdfPCell prixUnit = new PdfPCell(new Phrase("Prix Unitaire", smallBold));
		prixUnit.setPadding(8);
		prixUnit.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(prixUnit);

		PdfPCell prixTot = new PdfPCell(new Phrase("Prix Total", smallBold));
		prixTot.setPadding(8);
		prixTot.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(prixTot);

		PdfPCell devise = new PdfPCell(new Phrase("Devise", smallBold));
		devise.setPadding(5);
		devise.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(devise);

		table.setHeaderRows(1);

		for (Materiel mat : BatisUtils.getMateriauxDevisInitial(devis)) {

			PdfPCell nameCell = new PdfPCell(new Phrase(mat.getNom(), nomalFont));
			nameCell.setPaddingLeft(10);
			nameCell.setPaddingBottom(5);

			nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(nameCell);

			PdfPCell qualCell = new PdfPCell(new Phrase(mat.getQualitePrevu(), nomalFont));
			qualCell.setPaddingLeft(10);
			qualCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(qualCell);

			PdfPCell quantCell = new PdfPCell(new Phrase(mat.getQuantitePrevu(), nomalFont));
			quantCell.setPaddingLeft(10);
			quantCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(quantCell);

			PdfPCell uniteCell = new PdfPCell(new Phrase(mat.getUniteDemesure(), nomalFont));
			uniteCell.setPaddingLeft(10);
			uniteCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(uniteCell);

			PdfPCell prixUnitCell = new PdfPCell(new Phrase(mat.getPrixUnitairePrevu(), nomalFont));
			prixUnitCell.setPaddingLeft(10);
			prixUnitCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(prixUnitCell);

			PdfPCell prixTotCell = new PdfPCell(new Phrase(mat.getPrixTotPrevu(), nomalFont));
			prixTotCell.setPaddingLeft(10);
			prixTotCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(prixTotCell);

			PdfPCell deviseCell = new PdfPCell(new Phrase(mat.getDevise(), nomalFont));
			deviseCell.setPaddingLeft(10);
			deviseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(deviseCell);

		}

		PdfPCell ligneVide = new PdfPCell(new Phrase(" "));
		ligneVide.setColspan(7);
		table.addCell(ligneVide);

		String pourcentage = "";
		Double pourcent = devis.getPourcentageMO();
		if (pourcent != null) {
			pourcentage = String.valueOf(pourcent);
		}

		PdfPCell moinOeuvre = new PdfPCell(new Phrase("Main d'oeuvre ( " + pourcentage + "% )", smallBold));
		moinOeuvre.setPaddingLeft(10);
		moinOeuvre.setColspan(2);
		table.addCell(moinOeuvre);

		Double pritTot = BatisUtils.getSommeTotalPrevuDevis(devis, false);
		Double main = BatisUtils.getMainOeuvreDevis(pritTot, devis);
		Double sommeTot = main + pritTot;
		String mainValue = BatisUtils.getNumberFormatter(main);
		PdfPCell montantTot = new PdfPCell(new Phrase(mainValue, smallBold));
		montantTot.setHorizontalAlignment(Element.ALIGN_RIGHT);
		montantTot.setPaddingRight(10);
		montantTot.setColspan(4);
		montantTot.setPaddingBottom(5);
		table.addCell(montantTot);

		String deviseStringValue = "";
		if (devis.getListMateriaux() != null && !devis.getListMateriaux().isEmpty()) {
			deviseStringValue = devis.getListMateriaux().get(0).getDevise();
		}
		PdfPCell dev = new PdfPCell(new Phrase(deviseStringValue));
		dev.setPaddingLeft(10);
		table.addCell(dev);

		PdfPCell tot = new PdfPCell(new Phrase("TOTAL ", smallBold));
		tot.setPaddingLeft(10);
		tot.setColspan(2);
		table.addCell(tot);

		String montantTotalStringValue = BatisUtils.getNumberFormatter(sommeTot);
		PdfPCell montantTotal = new PdfPCell(new Phrase(montantTotalStringValue, smallBold));
		montantTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		montantTotal.setPaddingRight(10);
		montantTotal.setColspan(4);
		montantTotal.setPaddingBottom(5);
		table.addCell(montantTotal);

		PdfPCell deviseCell = new PdfPCell(new Phrase(deviseStringValue));
		deviseCell.setPaddingLeft(10);
		table.addCell(deviseCell);

		parapgraph.add(table);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	/**
	 * @return the currentDevis
	 */
	public Devis getCurrentDevis() {
		return currentDevis;
	}

	/**
	 * @param currentDevis the currentDevis to set
	 */
	public void setCurrentDevis(Devis currentDevis) {
		this.currentDevis = currentDevis;
	}

	/**
	 * @return the imageUtils
	 */
	public ImageUtils getImageUtils() {
		return imageUtils;
	}

	/**
	 * @param imageUtils the imageUtils to set
	 */
	@SuppressWarnings("static-access")
	public void setImageUtils(ImageUtils imageUtils) {
		this.imageUtils = imageUtils;
	}

	/**
	 * @return the parametresService
	 */
	public static ParametresService getParametresService() {
		return parametresService;
	}

	/**
	 * @param parametresService the parametresService to set
	 */
	public static void setParametresService(ParametresService parametresService) {
		GenerateDevisDocument.parametresService = parametresService;
	}

}
