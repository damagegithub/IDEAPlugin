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


public class AutoTakeName extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        e.getPresentation().setVisible(editor != null && selectionModel.hasSelection());
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (editor == null || project == null)
            return;

        //获取SelectionModel和Document对象
        SelectionModel selectionModel = editor.getSelectionModel();
        Document document = editor.getDocument();

        //拿到选中部分字符串
        String selectedText = selectionModel.getSelectedText();
        //得到选中字符串的起始和结束位置
        int startOffset = selectionModel.getSelectionStart();
        int endOffset = selectionModel.getSelectionEnd();


        Translator translator = new Translator();
        StringProcessing stringProcessing= new StringProcessing();
        //对文档进行操作部分代码，需要放入Runnable接口中实现，由IDEA在内部将其通过一个新线程执行
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    document.replaceString(startOffset,endOffset,
                            stringProcessing.toCamelCase(translator.translate("en",selectedText)));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        WriteCommandAction.runWriteCommandAction(project, runnable);

    }

    private String getSelectedType(Document document, int startOffset) {

        String text = document.getText().substring(0, startOffset).trim();
        int startIndex = text.lastIndexOf(' ');

        return text.substring(startIndex + 1);
    }
}
