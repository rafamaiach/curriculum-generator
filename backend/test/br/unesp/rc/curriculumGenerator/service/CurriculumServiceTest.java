package br.unesp.rc.curriculumGenerator.service;

import br.unesp.rc.curriculumGenerator.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CurriculumServiceTest {

    public static Curriculum getCurriculum() {
        //Create curriculum
        Curriculum curriculum = new Curriculum();

        curriculum.setName("INSERT CURR NAME");
        curriculum.setCountry("INSERT CURR COUNTRY");
        curriculum.setState("INSERT CURR STATE");
        curriculum.setCity("INSERT CURR CITY");

        curriculum.setCellPhone("INSERT CURR CELLPHONE");
        curriculum.setEmail("INSERT CURR EMAIL");
        curriculum.setGithub("INSERT CURR GITHUB");
        curriculum.setLinkedin("INSERT CURRR LINKEDIN");

        curriculum.setObjective("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum.");
        curriculum.setSummary("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget ligula eu lectus lobortis condimentum.");

        //Adds abilities
        Ability ability1 = new Ability();
        ability1.setName("Habilidade 1");
        curriculum.addAbility(ability1);

        Ability ability2 = new Ability();
        ability2.setName("Habilidade 2");
        curriculum.addAbility(ability2);

        Ability ability3 = new Ability();
        ability3.setName("Habilidade 3");
        curriculum.addAbility(ability3);

        //Adds formations
        Formation formation1 = new Formation();
        formation1.setName("Bacharelado em Ciências da Computação");
        formation1.setStartDate("2014");
        formation1.setFinalDate("Julho/2018");
        formation1.setInstitution("UNESP");
        formation1.setLocation("Rio Claro, São Paulo");
        curriculum.addFormation(formation1);

        Formation formation2 = new Formation();
        formation2.setName("Técnico em Informática");
        formation2.setStartDate("2012");
        formation2.setFinalDate("2013");
        formation2.setInstitution("ETEC Deputado Ary de Camargo Pedroso");
        formation2.setLocation("Piracicaba, São Paulo");
        curriculum.addFormation(formation2);

        //Adds languages
        Language language1 = new Language();
        language1.setName("Inglês");
        language1.setLanguageProeficiency(LanguageProeficiency.ADVANCED);
        curriculum.addLanguage(language1);

        Language language2 = new Language();
        language2.setName("Espanhol");
        language2.setLanguageProeficiency(LanguageProeficiency.FLUENT);
        curriculum.addLanguage(language2);

        //Adds Professional Experiences
        ProfessionalExperience professionalExperience1 = new ProfessionalExperience();
        professionalExperience1.setJob("Estagiário Desenvolvimento Web");
        professionalExperience1.setStartDate("Janeiro 2017");
        professionalExperience1.setFinalDate("Atualmente");
        professionalExperience1.setCompany("Empresa");
        professionalExperience1.setLocation("Cidade, Estado");
        professionalExperience1.setJobDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tempus dolor, non volutpat neque rutrum aliquam. Interdum et malesuada fames ac ante ipsum primis in faucibus.");
        curriculum.addProfesionalExperience(professionalExperience1);

        ProfessionalExperience professionalExperience2 = new ProfessionalExperience();
        professionalExperience2.setJob("Estagiário de Desenvolvimento");
        professionalExperience2.setStartDate("Agosto");
        professionalExperience2.setFinalDate("Dezembro 2016");
        professionalExperience2.setCompany("Empresa");
        professionalExperience2.setLocation("Cidade, São Paulo");
        professionalExperience2.setJobDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed luctus tempus dolor, non volutpat neque rutrum aliquam. Interdum et malesuada fames ac ante ipsum primis in faucibus.");
        curriculum.addProfesionalExperience(professionalExperience2);

        return curriculum;
    }

    @Test
    public void selectCurriculumByUserIdTest() {
        int userId = 1;
        CurriculumService curriculumService = FactoryService.getCurriculumService();
        List<Curriculum> curriculumList = curriculumService.selectCurriculumByUserId(userId);

        // Assert objects not null
        Assert.assertNotNull(curriculumList);

        for (Curriculum curriculum : curriculumList) {
            Assert.assertNotNull(curriculum.getAbilities());
            Assert.assertNotNull(curriculum.getFormations());
            Assert.assertNotNull(curriculum.getProfessionalExperiences());
            Assert.assertNotNull(curriculum.getLanguages());
        }
    }

    @Test
    public void insertCurriculumTest() {
        int userId = 1;
        Curriculum curriculumToBeInserted = getCurriculum();

        CurriculumService curriculumService = FactoryService.getCurriculumService();
        int curriculumId = curriculumService.insertCurriculum(curriculumToBeInserted, userId);

        Assert.assertNotEquals(-1, curriculumId);
    }
}
