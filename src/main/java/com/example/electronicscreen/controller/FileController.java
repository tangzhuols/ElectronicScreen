package com.example.electronicscreen.controller;

import com.example.electronicscreen.service.UtilService;
import com.example.electronicscreen.util.ResultVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by：mingwang
 * Company：Kengic
 * Date：2023/9/15
 * Time：9:43
 * description:
 */
@Slf4j
@RestController
@RequestMapping("/sys/file")
public class FileController {
    @Autowired
    private UtilService utilService;

    @GetMapping("/funConnection")
    public ResultVM funConnection(Integer id, String strText, String strText1, String strText2, String strText3, String strText4) {
        utilService.funConnection(id, strText, strText1, strText2, strText3, strText4);
        return ResultVM.ok();
    }

    @GetMapping("/funConnectionScreen")
    public ResultVM funConnectionScreen(Integer id, String text) {
        utilService.funConnectionScreen(id, text);
        return ResultVM.ok();
    }
}
