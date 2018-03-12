package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.*;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/result")
    public String result(@RequestParam("file") MultipartFile file, Model model) throws IllegalStateException, IOException {
        String fileName = file.getOriginalFilename();
        File newFile = File.createTempFile(fileName, ".txt");
        file.transferTo(newFile);
        JavaMetric jm = new JavaMetric(newFile.getAbsolutePath());
        
        List<String> listOfCode = jm.getCodeToList(newFile.getAbsolutePath());
        List<String> effLines = jm.getSortedCodeToList(listOfCode);
        
        //int cyclomaticMetric = jm.getCyclomaticMetric(effLines);

        model.addAttribute("noOfRawLines", jm.calculateNoOfPhyLines(listOfCode));       
        model.addAttribute("noOfEffLines", jm.calculateNoOfEffLines(effLines));       
        model.addAttribute("noOfLoops", jm.calculateNoOfLoops(effLines));
        model.addAttribute("noOfReturns", jm.calculateNoOfReturns(effLines));
        model.addAttribute("noOfConditions", jm.calculateNoOfConditions(effLines));
        
        model.addAttribute("vocabSize", jm.calculateVocabularySize());
        model.addAttribute("programLevel", jm.calculateProgramLevel());
        model.addAttribute("programLength", jm.calculateProgramLength());
        model.addAttribute("implTime", jm.calculateImplementationTime());
        model.addAttribute("volume", jm.calculateVolume());
        model.addAttribute("estNoBugs", jm.calculateEstimatedNoBugs());
        
        model.addAttribute("cyclomatic", jm.getCyclomaticMetric(effLines));
        
        model.addAttribute("maintainability", jm.calculateMainainabilityIndex(effLines));
        model.addAttribute("IndexCommentWeight", jm.calculateIndexCommentWeight());
        model.addAttribute("IndexWithoutComments", jm.calculateIndexWithoutComments(effLines));
        model.addAttribute("CommentPercentage", jm.calculateCommentPercentage());
        return "result";
    }

    @PostMapping("/pasteResult")
    @ResponseBody
    public String result(@RequestParam("code") String snippet ,Model model) throws IllegalStateException, IOException {
        return snippet;
    }

}
