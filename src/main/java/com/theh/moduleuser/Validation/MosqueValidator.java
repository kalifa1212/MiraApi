package com.theh.moduleuser.Validation;

import com.theh.moduleuser.Dto.MosqueDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MosqueValidator {
	
		public static boolean isValideTime(String heure) {
		
		String regex="([01]?[0-9]|2[0-3]):[0-5][0-9]";
		//String regexdate="([01]?[0-9]|2[0-9]|3[0-1])/([01]?[0-2])/([01]?[0-9]|2[0-9])";
		Pattern pattern=Pattern.compile(regex);
		if(heure==null) {
			return false;
		}
		
		Matcher matcher=pattern.matcher(heure);
		
		return matcher.matches();
		}

	public static List<String> validate(MosqueDto mosqueDto){
		List<String> errors = new ArrayList<>();
		if(mosqueDto==null ) {
			errors.add("Veuillez renseigner Les Informations de la mosque");
			log.error("La mosque vendredi doit etre  {}",mosqueDto.getIsVendredi());
		}
//
//		boolean b=isValideTime(mosqueDto.getBalte());
//		//boolean a=isValideTime(mosqueDto.getAsr());
//		boolean i=isValideTime(mosqueDto.getIcha());
//		boolean m=isValideTime(mosqueDto.getMagrib());
//		boolean dj=isValideTime(mosqueDto.getDjouma());
//		boolean s=isValideTime(mosqueDto.getSoub());
//		boolean z=isValideTime(mosqueDto.getZour());
//			//balte
//		if(!b) {
//			errors.add("Veuillez renseigner balte au format 00:00");
//		}
//		//asr
////		if(!a) {
////			errors.add("Veuillez renseigner asr au format 00:00");
////		}
//		//icha
//		if(!i) {
//			errors.add("Veuillez renseigner icha au format 00:00");
//		}
//		//magrib
//		if(!m) {
//			errors.add("Veuillez renseigner magrib au format 00:00");
//		}
//		//djouma
////		if(!dj) {
////			errors.add("Veuillez renseigner vendredi au format 00:00");
////		}
//		//soub
//		if(!s) {
//			errors.add("Veuillez renseigner soub au format 00:00");
//		}
//		//zour
//		if(!StringUtils.hasLength(mosqueDto.getZour())){
//
//		}
//		else if(!z) {
//			errors.add("Veuillez renseigner zour au format 00:00");
//		}
//		if(!StringUtils.hasLength(mosqueDto.getNom())) {
//		errors.add("Veuillez renseigner le nom de la mosque");
//		}
//		if(mosqueDto.getSuperficie()<=0) {
//			errors.add("Veuillez renseigner une supperficie realiste > 0 de la mosque");
//			log.error("La mosque vendredi doit etre sup {}",mosqueDto.getVendredi());
//		}
//		if(mosqueDto.getVendredi()!='O' && mosqueDto.getVendredi()!='N') {
//			errors.add("Veuillez renseigner l'etat de vendredi (O pour OUI et N pour NON )");
//			log.error("La mosque vendredi doit etre 1 {}",mosqueDto.getVendredi());
//		}
//		if(mosqueDto.getVendredi()=='O' && mosqueDto.getDjouma()==null) {
//			errors.add("Veuillez renseigner Djouma si c'est une mosque du vendredi au forma 00:00");
//			log.error("La mosque vendredi doit etre  2 {}",mosqueDto.getVendredi());
//		}
//		else if (mosqueDto.getDjouma()!=null) {
//			if(!dj) {
//				errors.add("Veuillez renseigner l'heur de la priere du vendredi (Djouma) au format 00:00");
//			}
//		}
		return errors;
	}
}
