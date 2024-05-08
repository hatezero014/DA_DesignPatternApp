package com.example.designpattern.Models;

import android.content.Context;
import android.util.Log;

import com.example.designpattern.Services.PatternService;

import java.util.List;
import java.util.Map;

public class MyUtilities {
     int[] comInt;
     int[] poInt;
     private static Map<String, Integer> complexity;
     private static Map<String, Integer> popularity;
     private PatternService patternService;

     public MyUtilities(Context context) {
          this.context = context;
          Init(context);
     }

     private void Init(Context context) {
          patternService = new PatternService(context);
          comInt = new int[]{1,2,2,1,1,1,3,2,2,1,3,2,2,1,2,2,3,2,1,1,1,3,2};
          poInt = new int[]{3,3,3,2,2,3,1,2,2,2,1,1,2,3,3,2,1,3,2,3,2,1,2};
          initComplexity();
          initPopularity();
     }

     private final Context context;


     private void initComplexity(){
//          List<Pattern> Patterns = patternService.GetAll(Pattern.class);
//          for (int i=0;i<Patterns.size();i++){
//               complexity.put(Patterns.get(i).getName(),comInt[i]);
//          }
     }
     private void initPopularity(){
//          List<Pattern> Patterns = patternService.GetAll(Pattern.class);
//          for (int i=0;i<Patterns.size();i++){
//               popularity.put(Patterns.get(i).getName(),poInt[i]);
//          }
     }

     public float getComRating(String PatternName){
          return 1f;
     }

     public float getPoRating(String PatternName){
          return 2f;
     }
}
