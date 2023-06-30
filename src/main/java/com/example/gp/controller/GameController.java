package com.example.gp.controller;

import com.example.gp.entity.Word;
import com.example.gp.service.AdminService;
import com.example.gp.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class GameController {


    @Autowired
    GameService gameService;

    @GetMapping("/game")
    public String gameView(Model model, HttpSession session){
        List<Word> words = gameService.findAll();

        Integer currentWordIndex = (Integer) session.getAttribute("currentWordIndex");
        if (currentWordIndex == null || currentWordIndex >= words.size()) {
            currentWordIndex = 0; // start over when we reach the end of the list
        }

        Word currentWord = words.get(currentWordIndex);
        model.addAttribute("currentWord", currentWord);
        session.setAttribute("currentWordIndex", currentWordIndex + 1); // increment the index for the next request

        return "game/gameView";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam("wordId") Long wordId, @RequestParam("guess") String guess) {
        Word word = gameService.findWordById(wordId);
        if (word != null) {
            String remainingLetters = word.getFullWord().substring(2);
            if (remainingLetters.equals(guess)) {
                // the guess was correct, do something here if necessary
            }
        }

        return "redirect:/game"; // redirect back to the game view to show the next word
    }

    @GetMapping("/addWord")
    public String addWord(){
        return "/admin/addWord";
    }

    @PostMapping("/addWord")
    public String addWord(@RequestParam("fullWord") String fullWord){

        Word word = new Word(fullWord);
        Word savedWord = gameService.addWord(word);
        System.out.println("savedWord = " + savedWord);
        return "/admin/addWord";
    }



    @PostMapping("/celeb/add")
    public String addCelebrity(@RequestParam("name") String name, @RequestParam("itemImgFile") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
            try{
                //파일 이름 가지고 오기
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                // 저장 경로 설정
                String uploadDir = "C:/shop/items";

                // 파일 저장 경로가 존재하지 않는다면 생성
                Path uploadPath = Paths.get(uploadDir);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                // 지정한 경로에 파일을 저장
                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());

                return "gameView";

            }catch(Exception e){

            }
        }

        return "error";
    }

    @GetMapping("/game/allPhotos")
    public String getAllPhotos(Model model){
        //경로에 있는 모든 사진을 가져오기
        File uploadFolder = new File("C:/shop/items");
        File[] files = uploadFolder.listFiles();

        List<String> photoFileNames = new ArrayList<>();

        // 파일 이름을 리스트에 넣기
        if(files != null){
            for(File file : files){
                photoFileNames.add(file.getName());
            }
        }
        System.out.println("photoFileNames = " + photoFileNames);
        model.addAttribute("photoFileNames", photoFileNames);

        return "gameView";
    }


}
