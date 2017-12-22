import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

public class needlemanWunsch{

    /**
     * @param match = eslesme degeri
     * @param mismatch(mismatch) = eslesmeme degeri
     * @param gap = bosluk ceza puani
     * @param bd2= ilk dizilim
     * @param bd1= ikinci dizilim
     */

    static int match, mismatch, gap;
    static int[][] matris;
    static String bd1, bd2;
    static String dizi1 = "", dizi2 = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Birinci dizilimi giriniz:");
        bd2 = sc.next();
        System.out.println("İkinci dizilimi giriniz:");
        bd1 = sc.next();
        System.out.println("Eşleşme değerini giriniz:");
        match = sc.nextInt();
        System.out.println("Eşleşmeme değerini giriniz:");
        mismatch = sc.nextInt();
        System.out.println("Ceza puanını giriniz:");
        gap = sc.nextInt();

        bd1.toUpperCase();
        bd2.toUpperCase();
        matrisTablo();
        System.out.println(goster());
        hizala();
        System.out.println(yazdir());
        /* JFrame jf = new JFrame("Needleman-Wunsch");
        jf.setSize(600, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton btnGoster = new JButton("Hesapla");
        final JTextField dizilim1 = new JTextField(15);
        final JTextField dizilim2 = new JTextField(15);
        final JTextField eslesme = new JTextField(5);
        final JTextField eslesmeme = new JTextField(5);
        final JTextField ceza = new JTextField(5);
        final Label diz1 = new Label("Birinci Dizilim");
        final Label diz2 = new Label("İkinci Dizilim");
        final Label m = new Label("Eslesme");
        final Label mm = new Label("Eslesmeme");
        final Label gp = new Label("Ceza puanı");
        jf.getContentPane().setLayout(new FlowLayout());

        jf.getContentPane().add(diz1);
        jf.getContentPane().add(dizilim1);
        jf.getContentPane().add(diz2);
        jf.getContentPane().add(dizilim2);
        jf.getContentPane().add(m);
        jf.getContentPane().add(eslesme);
        jf.getContentPane().add(mm);
        jf.getContentPane().add(eslesmeme);
        jf.getContentPane().add(gp);
        jf.getContentPane().add(ceza);
        jf.getContentPane().add(btnGoster);

        btnGoster.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                JTextField gos = new JTextField();
                String gelen1 = dizilim1.getText();
                String gelen2 = dizilim2.getText();
                bd2 = gelen1.toUpperCase();
                bd1 = gelen2.toUpperCase();
                match = Integer.parseInt(eslesme.getText());
                mismatch = Integer.parseInt(eslesmeme.getText());
                gap = Integer.parseInt(ceza.getText());
                matrisTablo();
                JOptionPane.showMessageDialog(null, goster());
                hizala();
                JOptionPane.showMessageDialog(null, yazdir());


            }
        });

        jf.setVisible(true); */

    }

    public static int[][] matrisTablo() {

        int i, j;
        matris = new int[bd1.length() + 1][bd2.length() + 1];

        for (i = 1; i < bd1.length() + 1; i++) {
            matris[i][0] = matris[i - 1][0] + gap;
        }

        for (j = 1; j < bd2.length() + 1; j++) {
            matris[0][j] = matris[0][j - 1] + gap;
        }


        for (i = 1; i < bd1.length() + 1; i++) {
            for (j = 1; j < bd2.length() + 1; j++) {
                //System.out.println(bd1.charAt(i-1) + "  " + bd2.charAt(j-1));

                if (i <= j) {
                    if (bd1.charAt(i - 1) != ' ' || bd2.charAt(j - 1) != ' ') {
                        if (bd1.charAt(i - 1) == bd2.charAt(j - 1)) {
                            matris[i][j] = maks(matris[i - 1][j] + match, matris[i - 1][j - 1] + match, matris[i][j - 1] + match);

                        } else {
                            matris[i][j] = maks(matris[i - 1][j] + mismatch, matris[i - 1][j - 1] + mismatch, matris[i][j - 1] + mismatch);
                        }

                    } else {
                        matris[i][j] = maks(matris[i - 1][j] + gap, matris[i - 1][j - 1] + gap, matris[i][j - 1] + gap);
                    }
                } else {

                    matris[i][j] = maks(matris[i - 1][j] + mismatch, matris[i - 1][j - 1] + mismatch, matris[i][j - 1] + mismatch);

                }

            }
        }
        return matris;
    }

    public static void hizala() {
        int i = bd1.length();
        int j = bd2.length();
        while (i > 0 && j > 0) {
            if (matris[i][j] == matris[i - 1][j - 1] + w(i, j)) {//çarpraz kontrol
                dizi1 += bd1.charAt(i - 1);
                dizi2 += bd2.charAt(j - 1);
                i--;
                j--;
                continue;
            } else if (matris[i][j] == matris[i][j - 1] - 1) { //sağ tarafın kontrolü
                dizi1 += "-";
                dizi2 += bd2.charAt(j - 1);
                j--;
                continue;
            } else { //yukarı kontrol
                dizi1 += bd1.charAt(i - 1);
                dizi2 += "-";
                i--;
                continue;
            }
        }
        dizi1 = new StringBuffer(dizi1).reverse()
                .toString();
        dizi2 = new StringBuffer(dizi2).reverse()
                .toString();

    }

    public static int w(int i, int j) {
        if (bd1.charAt(i - 1) == bd2.charAt(j - 1)) {
            return match;//parametreler değişirse şaşmasın diye
        } else {
            return -match;
        }
    }

    public static String yazdir() {
        return "En iyi Dizilim \n1. Dizilim: " + dizi2 + "\n" + "2. Dizilim: " + dizi1;

    }

    public static int maks(int a, int b, int c) {

        return Math.max(c, Math.max(a, b));
    }

    public static String goster() {
        String a = "";
        for (int i = 0; i < bd1.length() + 1; i++) {
            for (int j = 0; j < bd2.length() + 1; j++) {
                a += matris[i][j] + " |";

            }

            a += "\n";
        }
        return a;

    }

}
