package com.novikovav.multi_file_vue_component;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateAction extends AnAction {

    private final String lineSeparator = System.lineSeparator();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        CreateDialog createDialog = new CreateDialog();
        createDialog.show();
        if (createDialog.isOK()) {
            String fileName = createDialog.getFileName();
            if (fileName.isEmpty()) {
                return;
            }
            fileName = this.cleanFileName(fileName);
            Object psiElement = e.getDataContext().getData("psi.Element");
            VirtualFile virtualDirectoryFile = null;
            if (psiElement instanceof PsiFileImpl) {
                virtualDirectoryFile = ((PsiFileImpl) psiElement).getVirtualFile().getParent();
            } else if (psiElement instanceof PsiDirectory) {
                virtualDirectoryFile = ((PsiDirectory) psiElement).getVirtualFile();
            }
            if (virtualDirectoryFile == null) {
                return;
            }
            String dirName = virtualDirectoryFile.getCanonicalPath();

            this.createVueFile(dirName, fileName);
            this.createHtmlFile(dirName, fileName);
            this.createScriptFile(dirName, fileName);
            this.createStyleFile(dirName, fileName);

            virtualDirectoryFile.refresh(false, false);
        }
    }

    private String cleanFileName(String fileName) {
        if (fileName.endsWith(".vue")) {
            return fileName.substring(0, fileName.length() - 4);
        }
        return fileName;
    }

    /**
     * @param dirName  dir name
     * @param fileName file name
     */
    private void createVueFile(String dirName, String fileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dirName + "/" + fileName + ".vue"));
            bufferedWriter.write(String.format("<template src=\"./%s.html\"></template>%s", fileName, this.lineSeparator));
            bufferedWriter.write(String.format("<script src=\"./%s.js\"></script>%s", fileName, this.lineSeparator));
            bufferedWriter.write(String.format("<style scoped src=\"./%s.css\"></style>", fileName));
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getLocalizedMessage());
        }
    }

    /**
     * @param dirName  dir name
     * @param fileName file name
     */
    private void createHtmlFile(String dirName, String fileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dirName + "/" + fileName + ".html"));
            bufferedWriter.write(String.format("<div class=\"%s\">%s Component</div>", fileName, fileName));
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getLocalizedMessage());
        }
    }

    /**
     * @param dirName  dir name
     * @param fileName file name
     */
    private void createScriptFile(String dirName, String fileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dirName + "/" + fileName + ".js"));
            String scriptContent = String.format("" +
                    "export default {%s" +
                    "   name: '%s',%s" +
                    "   props: {%s" +
                    "   }%s" +
                    "}" +
                    "", this.lineSeparator, fileName, this.lineSeparator, this.lineSeparator, this.lineSeparator);
            bufferedWriter.write(scriptContent);
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getLocalizedMessage());
        }
    }

    /**
     * @param dirName  dir name
     * @param fileName file name
     */
    private void createStyleFile(String dirName, String fileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dirName + "/" + fileName + ".css"));
            bufferedWriter.write(String.format("" +
                    ".%s {%s" +
                    "   font-size: larger;%s" +
                    "}" +
                    "", fileName, this.lineSeparator, this.lineSeparator));
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getLocalizedMessage());
        }
    }
}
