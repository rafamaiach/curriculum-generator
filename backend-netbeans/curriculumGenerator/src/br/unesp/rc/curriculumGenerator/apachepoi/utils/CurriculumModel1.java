package br.unesp.rc.curriculumGenerator.apachepoi.utils;

import br.unesp.rc.curriculumGenerator.model.*;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;

public class CurriculumModel1 extends GenerateCurriculum {
    @Override
    protected void createDocumentHeader(Curriculum curriculum) {
        //Create the table that has the name inside the header
        XWPFHeader header = this.document.createHeader(HeaderFooterType.FIRST);
        XWPFTable headerTable = header.createTable(1, 1);

        //Set borders
        headerTable.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, -10, 5, "000000");
        headerTable.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 5, "000000");
        CTTblPr tblpro = headerTable.getCTTbl().getTblPr();
        CTTblBorders borders = tblpro.addNewTblBorders();
        borders.addNewRight().setVal(STBorder.NONE);
        borders.addNewBottom().setVal(STBorder.SINGLE);
        borders.addNewTop().setVal(STBorder.SINGLE);
        borders.addNewLeft().setVal(STBorder.NONE);

        XWPFTableCell tableCell = headerTable.getRow(0).getCell(0);

        //I need to remove the paragraph that is automatically created
        //'cause I will add it myself
        tableCell.removeParagraph(0);

        XWPFParagraph nameParagraph = tableCell.addParagraph();
        XWPFRun nameLine = nameParagraph.createRun();
        nameLine.setFontFamily("Arial");
        nameLine.setFontSize(12);
        nameLine.setBold(true);
        nameLine.setText(curriculum.getName());

        //Contact information
        header.createParagraph();

        //City, state, country
        XWPFRun cityStateCountryText = header.createParagraph().createRun();
        cityStateCountryText.setFontFamily("Arial");
        cityStateCountryText.setFontSize(10);
        cityStateCountryText.setText(curriculum.getCity());
        cityStateCountryText.setText(", ");
        cityStateCountryText.setText(curriculum.getState());
        cityStateCountryText.setText(", ");
        cityStateCountryText.setText(curriculum.getCountry());

        String cellphone = curriculum.getCellPhone();
        if (!(cellphone == null || cellphone.isEmpty())) {
            //Cellphone
            XWPFRun cellphoneText = header.createParagraph().createRun();
            cellphoneText.setFontFamily("Arial");
            cellphoneText.setFontSize(10);
            cellphoneText.setText("Celular: ");
            cellphoneText.setText(curriculum.getCellPhone());
        }

        String email = curriculum.getEmail();
        if (!(email == null || email.isEmpty())) {
            //Email
            XWPFRun emailText = header.createParagraph().createRun();
            emailText.setFontFamily("Arial");
            emailText.setFontSize(10);
            emailText.setText("E-mail: ");
            emailText.setText(curriculum.getEmail());
        }

        String github = curriculum.getGithub();
        if (!(github == null || github.isEmpty())) {
            //GitHub
            XWPFRun githubText = header.createParagraph().createRun();
            githubText.setFontFamily("Arial");
            githubText.setFontSize(10);
            githubText.setText("GitHub: ");
            githubText.setText(curriculum.getGithub());
        }

        String linkedin = curriculum.getLinkedin();
        if (!(linkedin == null || linkedin.isEmpty())) {
            //LinkedIn
            XWPFRun linkedInText = header.createParagraph().createRun();
            linkedInText.setFontFamily("Arial");
            linkedInText.setFontSize(10);
            linkedInText.setText("LinkedIn: ");
            linkedInText.setText(curriculum.getLinkedin());
        }

        //Separator
        this.addLineSeparator();
    }

    @Override
    protected void createDocumentContent(Curriculum curriculum) {
        createObjectiveSection(curriculum);
        createSummarySection(curriculum);
        createAbilitySection(curriculum);
        createFormationSection(curriculum);
        createLanguagesSection(curriculum);
        createExperienceSection(curriculum);
    }

    private void createObjectiveSection(Curriculum curriculum) {
        this.document.createParagraph();

        XWPFParagraph objectiveParagraph = this.document.createParagraph();
        objectiveParagraph.setIndentationLeft(249 * 5);
        objectiveParagraph.setIndentationFirstLine(-249 * 5);
        XWPFRun objectiveTitle = objectiveParagraph.createRun();
        objectiveTitle.setFontFamily("Arial");
        objectiveTitle.setFontSize(11);
        objectiveTitle.setBold(true);
        objectiveTitle.setText("Objetivo");
        //Add TAB character
        objectiveTitle.addTab();

        XWPFRun objectiveText = objectiveParagraph.createRun();
        objectiveText.setFontFamily("Arial");
        objectiveText.setFontSize(10);
        objectiveText.setText(curriculum.getObjective());

        //Separator
        this.addLineSeparator();
    }

    private void createSummarySection(Curriculum curriculum) {
        this.document.createParagraph();

        XWPFParagraph summaryParagraph = this.document.createParagraph();
        summaryParagraph.setIndentationLeft(249 * 5);
        summaryParagraph.setIndentationFirstLine(-249 * 5);
        summaryParagraph.setAlignment(ParagraphAlignment.BOTH);

        XWPFRun summaryTitle = summaryParagraph.createRun();
        summaryTitle.setFontFamily("Arial");
        summaryTitle.setFontSize(11);
        summaryTitle.setBold(true);
        summaryTitle.setText("Resumo");
        //Add TAB character
        summaryTitle.addTab();

        XWPFRun summaryText = summaryParagraph.createRun();
        summaryText.setFontFamily("Arial");
        summaryText.setFontSize(10);

        summaryText.setText(curriculum.getSummary());

        //Separator
        this.addLineSeparator();
    }

    private void createAbilitySection(Curriculum curriculum) {
        this.document.createParagraph();
        List<Ability> abilities = curriculum.getAbilities();

        XWPFParagraph abilityParagraph = this.document.createParagraph();
        abilityParagraph.setIndentationLeft(191 * 5);
        abilityParagraph.setSpacingAfter(11 * 5);

        XWPFRun abilityText = abilityParagraph.createRun();
        abilityText.setFontFamily("Arial");
        abilityText.setFontSize(11);
        abilityText.setBold(true);
        abilityText.setText("Habilidades");

        //Creates list of Abilities at document
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));

        //Set list style as "DECIMAL"
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewLvlText().setVal("%1.");
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = this.document.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        BigInteger numID = numbering.addNum(abstractNumID);

        //Adds all abilities at the list
        for (Ability ability : abilities) {
            XWPFParagraph paragraph = this.document.createParagraph();
            paragraph.setIndentationLeft(250 * 5);
            paragraph.setNumID(numID);
            XWPFRun run = paragraph.createRun();
            run.setFontFamily("Arial");
            run.setFontSize(10);
            run.setText(ability.getName());
        }

        //Separator
        this.addLineSeparator();
    }

    private void createFormationSection(Curriculum curriculum) {
        this.document.createParagraph();
        List<Formation> formations = curriculum.getFormations();

        XWPFParagraph formationParagraph = this.document.createParagraph();
        formationParagraph.setIndentationLeft(249 * 5);
        formationParagraph.setIndentationFirstLine(-249 * 5);

        XWPFRun formationTitle = formationParagraph.createRun();
        formationTitle.setFontFamily("Arial");
        formationTitle.setFontSize(11);
        formationTitle.setBold(true);
        formationTitle.setText("Graduação");
        //Add TAB character
        formationTitle.addTab();

        for (int i = 0; i < formations.size(); i++) {
            Formation formation = formations.get(i);

            //Add first formation
            XWPFRun formationText = formationParagraph.createRun();
            formationText.setFontFamily("Arial");
            formationText.setFontSize(10);
            formationText.setBold(true);
            formationText.setText(formation.getName());

            XWPFRun formationDateText = formationParagraph.createRun();
            formationDateText.setFontFamily("Arial");
            formationDateText.setFontSize(10);
            formationDateText.setItalic(true);
            formationDateText.setText(" - (" + formation.getStartDate() + " – " + formation.getFinalDate() + ")");
            //Next line
            formationDateText.addBreak();

            //Institution
            XWPFRun formationInstitutionText = formationParagraph.createRun();
            formationInstitutionText.setFontFamily("Arial");
            formationInstitutionText.setFontSize(10);
            formationInstitutionText.setItalic(true);
            formationInstitutionText.setBold(true);
            formationInstitutionText.setText(formation.getInstitution());
            formationInstitutionText.setText(", ");

            XWPFRun formationLocationText = formationParagraph.createRun();
            formationLocationText.setFontFamily("Arial");
            formationLocationText.setFontSize(10);
            formationLocationText.setItalic(true);
            formationLocationText.setText(formation.getLocation());

            if (formations.size() != i + 1) {
                formationLocationText.addBreak();
                formationLocationText.addBreak();
            }
        }

        this.addLineSeparator();
    }

    private void createLanguagesSection(Curriculum curriculum) {
        this.document.createParagraph();
        List<Language> languages = curriculum.getLanguages();

        XWPFParagraph languageParagraph = this.document.createParagraph();
        languageParagraph.setIndentationLeft(249 * 5);
        languageParagraph.setIndentationFirstLine(-249 * 5);
        XWPFRun languageTitle = languageParagraph.createRun();
        languageTitle.setFontFamily("Arial");
        languageTitle.setFontSize(11);
        languageTitle.setBold(true);
        languageTitle.setText("Idioma");
        //Add TAB character
        languageTitle.addTab();

        for (Language language : languages) {
            //First language
            XWPFRun languageText = languageParagraph.createRun();
            languageText.setFontFamily("Arial");
            languageText.setFontSize(10);
            languageText.setBold(true);
            languageText.setText(language.getName());
            languageText.setText(" - ");

            XWPFRun languageProeficiencyText = languageParagraph.createRun();
            languageProeficiencyText.setFontFamily("Arial");
            languageProeficiencyText.setFontSize(10);
            languageProeficiencyText.setText(language.getLanguageProeficiency().toString());
            languageProeficiencyText.addBreak();
        }
    }

    private void createExperienceSection(Curriculum curriculum) {
        List<ProfessionalExperience> professionalExperiences = curriculum.getProfessionalExperiences();

        XWPFRun languageTitle = this.document.createParagraph().createRun();
        languageTitle.setFontFamily("Arial");
        languageTitle.setFontSize(11);
        languageTitle.setBold(true);
        languageTitle.setText("Experiências Profissionais");

        for (ProfessionalExperience professionalExperience : professionalExperiences) {
            //First experience
            this.document.createParagraph();
            XWPFParagraph experienceParagraph = this.document.createParagraph();
            XWPFRun experienceJobText = experienceParagraph.createRun();
            experienceJobText.setFontFamily("Arial");
            experienceJobText.setFontSize(10);
            experienceJobText.setBold(true);
            experienceJobText.setText(professionalExperience.getJob());
            experienceJobText.setText(", ");

            XWPFRun jobDateText = experienceParagraph.createRun();
            experienceJobText.setFontFamily("Arial");
            experienceJobText.setFontSize(10);
            jobDateText.setText(professionalExperience.getStartDate());
            jobDateText.setText(" - ");
            jobDateText.setText(professionalExperience.getFinalDate());
            jobDateText.setText(", ");

            XWPFRun jobCompanyText = experienceParagraph.createRun();
            jobCompanyText.setFontFamily("Arial");
            jobCompanyText.setFontSize(10);
            jobCompanyText.setText(professionalExperience.getCompany());
            jobCompanyText.setText(", ");

            XWPFRun jobLocationText = experienceParagraph.createRun();
            jobLocationText.setFontFamily("Arial");
            jobLocationText.setFontSize(10);
            jobLocationText.setText(professionalExperience.getLocation());

            XWPFParagraph jobDescriptionParagraph = this.document.createParagraph();
            jobDescriptionParagraph.setIndentationLeft(150 * 5);
            XWPFRun jobDescriptionText = jobDescriptionParagraph.createRun();
            jobDescriptionText.setFontFamily("Arial");
            jobDescriptionText.setFontSize(10);
            jobDescriptionText.setItalic(true);
            jobDescriptionText.setText(professionalExperience.getJobDescription());
        }
    }

    private void addLineSeparator() {
        //Separator
        XWPFRun separatorText = this.document.createParagraph().createRun();
        separatorText.setFontFamily("Arial");
        separatorText.setFontSize(10);
        separatorText.setText("_____________________________________________________________________________");
    }

    @Override
    protected void createDocumentFooter(Curriculum curriculum) {
    }
}
