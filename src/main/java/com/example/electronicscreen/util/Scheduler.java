package com.example.electronicscreen.util;


import com.example.electronicscreen.service.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by：mingwang
 * Company：Kengic
 * Date：2022/3/20
 * Time：12:24
 * Annotation:定时器 将登录信息写入到数据库中
 */
//@Component
@Slf4j
public class Scheduler {

    @Autowired
    private UtilService utilService;
    int size = 1;


    //每天6：05执行
//    @Scheduled(fixedDelay = 30000)
    public void testTasks() {
        size++;
        String s = String.format("%04d", size);
        for (int i = 1; i < 10; i++) {
            if (size > 1000) size = 1;
            utilService.funConnection(i, i + "出库口", "CYBS01-240122" + s, "5750/5750", "10234000" + i, "正常");
        }

    }

}
