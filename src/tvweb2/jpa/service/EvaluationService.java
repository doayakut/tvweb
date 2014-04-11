package tvweb2.jpa.service;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import tvweb2.jpa.evaluation.Experience;
import tvweb2.jpa.evaluation.Familiarity;
import tvweb2.jpa.evaluation.PreStudy;
import tvweb2.jpa.evaluation.Questionnaire;
import tvweb2.jpa.evaluation.Screenshots;
import tvweb2.jpa.evaluation.Tasks;

public class EvaluationService {
	protected EntityManager em;
	protected UserTransaction ut;
	
	
	public EvaluationService(EntityManager em) {
		this.em = em;
	}
	
	public PreStudy setPreStudyAge(PreStudy p, String answer){
		if(p != null){
			p.setAge(answer);
		}
		return p;
	}
	
    public PreStudy setPreStudyGender(PreStudy p, String answer){
        if(p != null){
            p.setGender(answer);
        }
        return p;
    }

    public PreStudy setPreStudyEdu(PreStudy p, String answer){
        if(p != null){
            p.setEdu(answer);
        }
        return p;
    }
    public PreStudy setPreStudyExp(PreStudy p, String answer){
        if(p != null){
            p.setExp(answer);
        }
        return p;
    }
    public PreStudy setPreStudyOwner(PreStudy p, String answer){
        if(p != null){
            p.setOwner(answer);
        }
        return p;
    }
    public PreStudy setPreStudyFreq(PreStudy p, String answer){
        if(p != null){
            p.setFreq(answer);
        }
        return p;
    }
    public PreStudy setPreStudyAim(PreStudy p, String answer1, String answer2, String answer3 ){
        if(p != null){
            p.setAim(answer1+ " ; " + answer2 + " ; " + answer3);
        }
        return p;
    }
    public PreStudy setPreStudyTools(PreStudy p, String answer){
        if(p != null){
            p.setTools(answer);
        }
        return p;
    }
    public PreStudy setPreStudyProbs(PreStudy p, String answer){
        if(p != null){
            p.setProbs(answer);
        }
        return p;
    }
    public PreStudy setPreStudySols(PreStudy p, String answer){
        if(p != null){
            p.setSols(answer);
        }
        return p;
    }
    public PreStudy setPreStudyDisabilities(PreStudy p, String answer){
        if(p != null){
            p.setDisabilities(answer);
        }
        return p;
    }
    public PreStudy setPreStudyBlindSince(PreStudy p, String answer){
        if(p != null){
            p.setBlindSince(answer);
        }
        return p;
    }
    
	public Familiarity setFamiliarityValue(Familiarity f, int index, Float value){
		if(f != null){
			f.setAnswer(index-1, value);
		
		}
		return f;
	}
	public Tasks setTaskAnswerAnswer(Tasks t, int index, String answer){
		if(t != null){
			t.setAnswer(index-1, answer);
		}
		return t;
		
	}
	public Questionnaire setQuesAnswerValues(Questionnaire q, int index, Float value){
		if(q != null){
			q.setAnswer(index - 1, value);
		}
		return q;
	}
	public Questionnaire setQuesFeedback(Questionnaire q, String value){
		if(q != null){
			q.setFeedback(value);
		}
		return q;
	}
	public Experience setExperienceAnswers(Experience e, String answer1, String answer2){
		if(e != null){
			e.setProbs(answer1);
			e.setSols(answer2);
		}
		return e;
	}
	public Screenshots setScreenshotValues(Screenshots s, int index, String val1, String val2, String val3) {
		if (s != null) {
			s.setAnswer(index - 1, (val1 + ";" + val2 + ";" + val3));	
		}
		return s;
	}
}
