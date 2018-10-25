package com.sa;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sa.dao.DAOFactory;
import com.sa.dao.ExcelReader;
import com.sa.dao.KMLDAO;
import com.sa.dao.PostcodeDAO;
import com.sa.model.SAModel;
import com.sa.to.PostcodeTO;
import com.sa.to.SurveyDataTO;
import com.sa.to.SurveyTO;

public class SAController extends AbstractController {
	
	private static SAController instance=null;
	
	public static final String P_POLYGON_AREAS="PolygonAreas";
	
	public static final String P_SURVEYS="Surveys";
	
	public static final String A_REMOVE_SURVEY="RemoveSurvey";
	public static final String A_ADD_SURVEY="AddSurvey";
	
	private PostcodeDAO postcodeDAO=null; 
	private KMLDAO kmlDAO=null;
	
	private SAController(){
		super();
		initialize();
	}
	
	private void initialize(){
		DAOFactory factory=DAOFactory.getDAOFactory(DAOFactory.ACCESS);
		factory.setDatabasePath("C:/Users/ville.susi/Sales Analyzer/IKEA_VC.mdb");
		postcodeDAO=factory.getPostcodeDAO();
		kmlDAO=new KMLDAO();
	}
	
	public static SAController getInstance(){
		if(instance==null){
			instance=new SAController();
		}
		return instance;
	}
	
	public void loadAreaData(File kmlFile){
		
		List<PolygonArea> areas=kmlDAO.getPolygonAreas(kmlFile);
		List<PostcodeTO> postcodeList=postcodeDAO.getAllPostcodes();
		
		for(PolygonArea a : areas){
			a.setPostcodes(getPostcodesInsideArea(postcodeList,a.getPolygon()));
			System.out.println("postcode count:"+a.getPostcodes().size());
		}
		//---------------väliaikainen------------------------
		//PolygonArea kehaArea=areas.get(0);
		//postcodeDAO.createPostcodeTable("tbl_tmp_keha_III", kehaArea.getPostcodes());
		//---------------------------------------------------
		this.setModelProperty(SAController.P_POLYGON_AREAS, areas);
		
	}
	
	private List<PostcodeTO> getPostcodesInsideArea(List<PostcodeTO> postcodeList,Path2D.Double areaPolygon){
		
		List<PostcodeTO> areaPostcodes=new ArrayList<PostcodeTO>();
		
		for(PostcodeTO pc : postcodeList){
			Point2D.Double point=new Point2D.Double(pc.getLongitude(), pc.getLattitude());
			if(areaPolygon.contains(point)){
				areaPostcodes.add(pc);
			}
		}
		
		return areaPostcodes;
	}
	
	public void loadSurvey(int surveyId){
		SurveyTO survey=postcodeDAO.getSurvey(surveyId);
		//System.out.println(survey.getName());
		//System.out.println(survey.getData().size());
		this.action(SAController.A_ADD_SURVEY,survey);
	}
	
	public void loadSurvey(File file){
		if(isSurveyFile(file)){
			ExcelReader reader=new ExcelReader(file);
			List<SurveyDataTO> dataList=reader.getSurveyDataList();
			System.out.println(dataList.size());
			SurveyTO survey=new SurveyTO();
			survey.setName(file.getName());
			survey.setData(dataList);
			this.action(SAController.A_ADD_SURVEY,survey);
		}
	}
	
	public void removeSurvey(SurveyTO survey){
		this.action(SAController.A_REMOVE_SURVEY, survey);
	}
	
	private boolean isSurveyFile(File file){
		return true;
	}
	
	
}
