package com.itmo.jph;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class MainAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (editor == null || project == null)
            return;

        //获取SelectionModel和Document对象
        SelectionModel selectionModel = editor.getSelectionModel();
        if(!selectionModel.hasSelection())
        {
            Messages.showMessageDialog(project,"Какая ~ хорошая ~ погода ！", "Information", Messages.getInformationIcon());
        }
        //拿到选中部分字符串
        String selectedText = selectionModel.getSelectedText();

        Translator translator = new Translator();
        StringProcessing stringProcessing= new StringProcessing();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //genGetterAndSetter为生成getter和setter函数部分
                //document.insertString(insertOffset, genGetterAndSetter(selectedText, type));
                try {
                    Messages.showMessageDialog(project,
                            String.format(translator.translate("ru",stringProcessing.toCommonCase(selectedText))), "Information", Messages.getInformationIcon());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };

        //加入任务，由IDEA调度执行这个任务
        WriteCommandAction.runWriteCommandAction(project, runnable);

    }
    
    //Select type
    private String getSelectedType(Document document, int startOffset) {

        String text = document.getText().substring(0, startOffset).trim();
        int startIndex = text.lastIndexOf(' ');

        return text.substring(startIndex + 1);
    }
}
