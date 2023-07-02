package com.example.gp.controller;

import com.example.gp.entity.Record;
import com.example.gp.entity.Word;
import com.example.gp.service.GameService;
import com.example.gp.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    RecordService recordService;

    //게임 선택
    @GetMapping("/choice")
    public String choice(){
        return "choice";
    }

    //사자성어 게임
    @GetMapping("/game1")
    public String gameView(Model model, HttpServletRequest request){
        //사자성어 문제 가져오기
        List<Word> words = gameService.findAll();
        model.addAttribute("words", words);

        //쿠키에서 닉네임 가져오기
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals("nick")) {
                    String nick = cookie.getValue();
                    model.addAttribute("nick", nick);
                }
            }
        }
        return "game/game1";
    }

    //게임 기록
    @PostMapping ("/record/add")
    public String addRecord(@RequestBody Record record){
        //기록 저장
        recordService.save(record);

        return "choice";
    }



    @GetMapping("/record")
    public String viewRecord(@RequestParam("value") int gameNum,Model model){
        List<Record> records = recordService.find10ByGame(gameNum);
        model.addAttribute("records",records);
        return "record";
    }


    @GetMapping("/addWord")
    public String addWord(){
        return "/admin/addWord";
    }

    @PostMapping("/addWord")
    public String addWord(@RequestParam("fullWord") String fullWord){

        Word word = new Word(fullWord);
        Word savedWord = gameService.addWord(word);
        return "/admin/addWord";
    }
}



//    @PostMapping("/celeb/add")
//    public String addCelebrity(@RequestParam("name") String name, @RequestParam("itemImgFile") MultipartFile file) throws Exception {
//        if(!file.isEmpty()){
//            try{
//                //파일 이름 가지고 오기
//                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//                // 저장 경로 설정
//                String uploadDir = "C:/shop/items";
//
//                // 파일 저장 경로가 존재하지 않는다면 생성
//                Path uploadPath = Paths.get(uploadDir);
//                if(!Files.exists(uploadPath)){
//                    Files.createDirectories(uploadPath);
//                }
//
//                // 지정한 경로에 파일을 저장
//                Path filePath = uploadPath.resolve(fileName);
//                file.transferTo(filePath.toFile());
//
//                return "gameView";
//
//            }catch(Exception e){
//
//            }
//        }
//
//        return "error";
//    }
//
//    @GetMapping("/game/allPhotos")
//    public String getAllPhotos(Model model){
//        //경로에 있는 모든 사진을 가져오기
//        File uploadFolder = new File("C:/shop/items");
//        File[] files = uploadFolder.listFiles();
//
//        List<String> photoFileNames = new ArrayList<>();
//
//        // 파일 이름을 리스트에 넣기
//        if(files != null){
//            for(File file : files){
//                photoFileNames.add(file.getName());
//            }
//        }
//        System.out.println("photoFileNames = " + photoFileNames);
//        model.addAttribute("photoFileNames", photoFileNames);
//
//        return "gameView";
//    }