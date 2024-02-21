package com.example.electronicscreen.serviceimpl;

import com.example.electronicscreen.service.UtilService;
import com.example.electronicscreen.util.DllLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by：mingwang
 * Company：Kengic
 * Date：2022/12/4
 * Time：15:10
 * description:
 */
@Slf4j
@Service("UtilService")
public class UtilServiceImpl implements UtilService {

    private static volatile DllLibrary lib = DllLibrary.INSTANCE;

    @Override
    public void funConnection(int m_iCardNum, String strText, String strText1, String strText2, String strText3, String strText4) {
        if (this.OnRealtimeConnect(m_iCardNum)) {
            final int i = OnAddProgram(m_iCardNum);
            OnAddText(m_iCardNum, 0, 0, 192, 24, strText, i, 14, 1);

//            //第二行
            OnAddText(m_iCardNum, 0, 23, 36, 24, "单号", i, 6, 1);    //文本信息
            OnAddText(m_iCardNum, 35, 23, 157, 24, strText1, i, 10, 1);    //文本信息

//            //第三行
            OnAddText(m_iCardNum, 0, 46, 36, 24, "数量", i, 6, 1);    //文本信息
            OnAddText(m_iCardNum, 35, 46, 157, 24, strText2, i, 10, 1);    //文本信息
//            //第四行
            OnAddText(m_iCardNum, 0, 69, 36, 27, "托盘", i, 6, 1);    //文本信息
            OnAddText(m_iCardNum, 35, 69, 108, 27, strText3, i, 10, 1);    //文本信息
            OnAddText(m_iCardNum, 142, 69, 50, 27, strText4, i, 10, 1);    //文本信息
            OnSendToScreen(m_iCardNum);//发送节目到显示屏
            OnRealtimeDisConnect(m_iCardNum);
        }
    }

    @Override
    public void funConnectionScreen(int m_iCardNum, String strText) {
//        if (this.OnRealtimeConnect(m_iCardNum)) {
//            OnDelAllProgram(m_iCardNum);
//            int i = -1;
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 0, 0, 160, 20, "2-1号出库点", i);    //文本信息
//
//            //第二行
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 0, 21, 64, 20, "单号", i);    //文本信息
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 65, 21, 32, 20, "托盘", i);    //文本信息
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 97, 21, 64, 20, "数量", i);    //文本信息
//
//            //第三行
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 0, 41, 64, 20, "EA000000081", i);    //文本信息
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 65, 41, 32, 20, "10321", i);    //文本信息
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 97, 41, 64, 20, "5750/5750", i);    //文本信息
//            //第四行
//            i = OnAddProgram(m_iCardNum);//添加节目
//            OnAddSingleText(m_iCardNum, 0, 61, 160, 20, "正常", i);    //文本信息
//            OnSendToScreen(m_iCardNum);
//        }
    }

    /**
     * 链接显示屏
     *
     * @param m_iCardNum
     * @return
     */
    public boolean OnRealtimeConnect(int m_iCardNum) {
        synchronized (UtilServiceImpl.class) {
            String strEQ2008_Dll_Set_Path = "D:\\opt\\dll\\EQ2008_Dll_Set.ini";
            lib.User_SetWorkDir("D:\\opt\\dll");
            lib.User_ReloadIniFile(strEQ2008_Dll_Set_Path);
            if (!lib.User_RealtimeConnect(m_iCardNum)) {
                log.info("实时发送数据建立连接失败！编号：{}", m_iCardNum);
                return false;
            } else {
                log.info("实时发送数据建立连接成功！：{}", m_iCardNum);
                return true;
            }
        }

    }

    /**
     * 实时发送数据，断开连接
     *
     * @param m_iCardNum
     */
    public static void OnRealtimeDisConnect(int m_iCardNum) {
        synchronized (UtilServiceImpl.class) {
            if (!lib.User_RealtimeDisConnect(m_iCardNum)) {
                log.info("实时发送数据断开连接失败！编号：{}", m_iCardNum);
            } else {
                log.info("实时发送数据断开连接成功！编号：{}", m_iCardNum);
            }
        }
    }


    /**
     * 删除所有节目
     *
     * @param m_iCardNum
     */
    public static void OnDelAllProgram(int m_iCardNum) {
        if (!lib.User_DelAllProgram(m_iCardNum)) {
            log.info("删除节目失败！编号：{}", m_iCardNum);
        } else {
            log.info("请首先添加节目！编号：{}", m_iCardNum);
        }
    }

    /**
     * 发送数据到显示屏
     *
     * @param m_iCardNum
     */
    public static void OnSendToScreen(int m_iCardNum) {
        if (!lib.User_SendToScreen(m_iCardNum)) {
            log.info("数据发送失败！编号：{}", m_iCardNum);
        } else {
            log.info("数据发送成功！编号：{}", m_iCardNum);
        }

    }

    /**
     * 实时发送文本内容
     */
    public static void OnRealtimeSendText(int m_iCardNum, int x, int y, int iWidth, int iHeight, String strText) {
        synchronized (UtilServiceImpl.class) {
            DllLibrary.User_FontSet fontSet = new DllLibrary.User_FontSet();
            fontSet.strFontName = "宋体"; //字体的名称
            fontSet.iFontSize = 12; //字体的大小
            fontSet.bFontBold = false; //字体是否加粗
            fontSet.bFontItaic = false; //字体是否是斜体
            fontSet.bFontUnderline = false; //字体是否带下划线
            fontSet.colorFont = 0xFFFF; //字体的颜色
            fontSet.iAlignStyle = 0; //对齐方式
            fontSet.iVAlignerStyle = 0; //对齐方式
            fontSet.iRowSpace = 0; //行间距
            log.info("{}", fontSet);
            log.info("{}");
            if (!lib.User_RealtimeSendText(m_iCardNum, x, y, iWidth, iHeight, strText, fontSet)) {
                log.info("发送实时文本失败！{} -- {} --{} --{} --{} --{}", m_iCardNum, x, y, iWidth, iHeight, strText);
            } else {
                log.info("发送实时文本成功！{} -- {} --{} --{} --{} --{}", m_iCardNum, x, y, iWidth, iHeight, strText);
            }
        }
    }

    public static int OnAddProgram(int m_iCardNum) {
        synchronized (UtilServiceImpl.class) {
            int m_iProgramIndex = lib.User_AddProgram(m_iCardNum, false, 10);
            log.info("添加节目" + m_iProgramIndex);
            return m_iProgramIndex;
        }
    }

    /**
     * 添加文本窗
     */
    public static void OnAddText(int m_iCardNum, int x, int y, int iWidth, int iHeight, String strText,
                                 int m_iProgramIndex, int fontSize, int iActionType) {
        synchronized (UtilServiceImpl.class) {
            DllLibrary.User_Text Text = new DllLibrary.User_Text();
            System.setProperty("jna.encoding", "GBK");
            Text.BkColor = 0;
            Text.chContent = strText;

            Text.PartInfo.FrameColor = 0;
            Text.PartInfo.iFrameMode = 52;
            Text.PartInfo.iHeight = iHeight;
            Text.PartInfo.iWidth = iWidth;
            Text.PartInfo.iX = x;
            Text.PartInfo.iY = y;

            Text.FontInfo.bFontBold = false;
            Text.FontInfo.bFontItaic = false;
            Text.FontInfo.bFontUnderline = false;
            Text.FontInfo.colorFont = 0xFFFF;
            Text.FontInfo.iFontSize = fontSize;
            Text.FontInfo.strFontName = "";
            Text.FontInfo.iAlignStyle = 1;
            Text.FontInfo.iVAlignerStyle = 1;
            Text.FontInfo.iRowSpace = 0;


            if (iActionType != 1) {
                Text.MoveSet.bClear = true;
                Text.MoveSet.iActionSpeed = 4;
                Text.MoveSet.iActionType = iActionType;
                Text.MoveSet.iHoldTime = 50;
                Text.MoveSet.iClearActionType = 0;
                Text.MoveSet.iClearSpeed = 4;
                Text.MoveSet.iFrameTime = 10;
            } else {
                Text.MoveSet.bClear = false;
                Text.MoveSet.iActionSpeed = 0;
                Text.MoveSet.iActionType = iActionType;
                Text.MoveSet.iHoldTime = 0;
                Text.MoveSet.iClearActionType = 0;
                Text.MoveSet.iClearSpeed = 0;
                Text.MoveSet.iFrameTime = 0;
            }


            if (-1 == lib.User_AddText(m_iCardNum, Text, m_iProgramIndex)) {
                log.info("添加文本失败！");
            } else {
                log.info("添加文本成功！");
            }
        }
    }

    /**
     * 添加单行文本
     */
    public static void OnAddSingleText(int m_iCardNum, int x, int y, int iWidth, int iHeight, String strText,
                                       int m_iProgramIndex) {
        synchronized (UtilServiceImpl.class) {
            DllLibrary.User_SingleText singleText = new DllLibrary.User_SingleText();
            System.setProperty("jna.encoding", "GBK");
            singleText.BkColor = 0;
            singleText.chContent = strText;
            singleText.PartInfo.iHeight = iHeight;
            singleText.PartInfo.iWidth = iWidth;
            singleText.PartInfo.iX = x;
            singleText.PartInfo.iY = y;
            singleText.PartInfo.iFrameMode = 52;
            singleText.FontInfo.bFontBold = false;
            singleText.FontInfo.bFontItaic = false;
            singleText.FontInfo.bFontUnderline = false;
            singleText.FontInfo.colorFont = 0xff;
            singleText.FontInfo.iFontSize = 10;
            singleText.FontInfo.iRowSpace = 0;
            singleText.FontInfo.iAlignStyle = 2;
            singleText.FontInfo.iVAlignerStyle = 2;
            singleText.FontInfo.strFontName = "宋体";
            singleText.MoveSet.bClear = false;
            singleText.MoveSet.iActionSpeed = 0;
            singleText.MoveSet.iActionType = 1;
            singleText.MoveSet.iHoldTime = 0;
            singleText.MoveSet.iClearActionType = 0;
            singleText.MoveSet.iClearSpeed = 0;
            singleText.MoveSet.iFrameTime = 0;
            log.info("{}", singleText.chContent);

            if (-1 == lib.User_AddSingleText(m_iCardNum, singleText, m_iProgramIndex)) {
                System.out.println("添加单行文本失败！");

            } else {
                System.out.println("添加单行文本成功！");
            }
        }
    }


    /**
     * 函数：关闭显示屏
     *
     * @param m_iCardNum
     */
    public static void OnCloseScreen(int m_iCardNum) {
        if (!lib.User_CloseScreen(m_iCardNum)) {
            log.info("关闭显示屏失败！编号：{}", m_iCardNum);
        } else {
            log.info("关闭显示屏成功！编号：{}", m_iCardNum);
        }
    }

    /**
     * 清空控制卡节目
     *
     * @param m_iCardNum
     */
    public static void OnRealtimeScreenClear(int m_iCardNum) {
        if (!lib.User_RealtimeScreenClear(m_iCardNum)) {
            log.info("清空控制卡节目失败！编号：{}", m_iCardNum);
        } else {
            log.info("清空控制卡节目成功！编号：{}", m_iCardNum);
        }
    }

}
