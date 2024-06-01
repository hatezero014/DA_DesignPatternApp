package com.example.designpattern.Models;


import android.content.Context;
import android.util.Log;

import com.example.designpattern.Services.PatternService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyUtilities {
     float[] comInt = {1,2,2,1,1,1,3,2,2,1,3,2,2,1,2,2,3,2,1,1,1,3,2};
     float[] poInt = {3,3,3,2,2,3,1,2,2,2,1,1,2,3,3,2,1,3,2,3,2,1,2};
     private static Map<String, Float> complexity;
     private static Map<String, Float> popularity;
     private PatternService patternService;

     public MyUtilities(Context context) {
          this.context = context;
          Init(context);
     }

     private void Init(Context context) {
          patternService = new PatternService(context);
          initComplexity();
          initPopularity();
     }

     private final Context context;


     private void initComplexity(){
          List<Pattern> Patterns = patternService.GetAll(Pattern.class);
          complexity = new HashMap<>();
          for (int i=0;i<23;i++){
               complexity.put(Patterns.get(i).getName(),comInt[i]);
          }
     }
     private void initPopularity(){
          List<Pattern> Patterns = patternService.GetAll(Pattern.class);
          popularity = new HashMap<>();
          for (int i=0;i<23;i++){
               Log.i("name",Patterns.get(i).getName());
               popularity.put(Patterns.get(i).getName(),poInt[i]);
          }
     }

     public float getComRating(String PatternName){
          return complexity.get(PatternName);
     }

     public float getPoRating(String PatternName){
          return popularity.get(PatternName);
     }
}
