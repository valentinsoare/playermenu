package basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\033[0;91m";
    public static final String WHITE = "\033[0;30m";
    public static final String RED_BOLD = "\033[1;91m";


    private static Album findAlbum(String albumName, List<Album> albumList) {
        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getNameOfAlbum().equalsIgnoreCase(albumName)) {
                return albumList.get(i);
            }
        }
        return null;
    }

    private static Song findSong(String songName, List<Album> albumList) {
        for (int i = 0; i < albumList.size(); i++) {
            Album album = albumList.get(i);
            for (int j = 0; j < album.getAlbumListOfSongs().size(); j++) {
                if (album.returnStringFindSong(album.getAlbumListOfSongs().get(j).getTitle()).equals(songName)) {
                    return album.getAlbumListOfSongs().get(j);
                }
            }
        }
        return null;
    }

    private static boolean addAlbum(String albumName, List<Album> albumList) {
        if (isInteger(albumName) || albumName.equals("")) {
            System.out.println("\n**WRONG_INPUT - you need to enter again the name of the album.\n");
            return false;
        } else {
            Album album = findAlbum(albumName, albumList);
            if (album != null) {
                System.out.println("\n**Album already in the list.\n");
                return false;
            } else {
                albumList.add(new Album(albumName, new ArrayList<>()));
                return true;
            }
        }
    }

    private static boolean removeAlbum(String albumName, List<Album> albumList) {
        boolean var = false;
        Album album = findAlbum(albumName.toLowerCase(), albumList);
        if (album != null) {
            albumList.remove(album);
            var = true;
        } else {
            System.out.println("\n*Mentioned album is not in the list.\n");
        }
        return var;
    }

    private static void removeAlbumsFromTheList(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = false;
        while (!check) {
            System.out.print("*Enter the name of the album you want to remove: ");
            String input = reader.readLine().trim();
            if (isInteger(input) || input.equals("")) {
                System.out.println("\n*You need to enter the name of the album again.\n");
            } else {
                removeAlbum(input, albumList);
                check = true;
            }
        }
    }

    private static void printAlbums(List<Album> albumList) {
        System.out.print("*We have the following albums available for us: \n");
        if (albumList.isEmpty()) {
            System.out.println("\n*No albums in albums list.\n");
        } else {
            for (int i = 0; i < albumList.size(); i++) {
                System.out.println("- " + albumList.get(i).getNameOfAlbum());
            }
        }
    }

    private static void printSongsInAlbum(String nameOfAlbum, List<Album> albumList) {
        Album album = findAlbum(nameOfAlbum, albumList);
        if (album != null) {
            System.out.print("\nAlbum: " + album.getNameOfAlbum() + "\nSongs:\n");
            album.printSongs();
        } else {
            System.out.println("\n*Issues - cannot find the given album in the list.");
        }
    }

    private static int findIndex(String albumName, List<Album> albumList) {
        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getNameOfAlbum().equalsIgnoreCase(albumName)) {
                return i;
            }
        }
        return -1;
    }

    private static void replaceAlbum(String oldAlbumName, String newAlbumName, List<Album> albumList) {
        int position = findIndex(oldAlbumName, albumList);
        removeAlbum(oldAlbumName, albumList);
        albumList.add(position, new Album(newAlbumName, new ArrayList<>()));
    }

    private static void printLines(int numberOfLines) {
        for (int i = 0; i < numberOfLines; i++) {
            System.out.print("--");
        }
        System.out.println();
    }

    private static void intro(String msg) {
        System.out.print(WHITE + "|" + RESET);
        for (int i = 0; i < msg.length(); i++) {
            System.out.print(WHITE + "-" + RESET);
        }
        System.out.print(WHITE + "|" + RESET);
        System.out.println("\n" + WHITE + "|" + RESET + RED_BOLD + msg + RESET + WHITE + "|" + RESET);
        System.out.print(WHITE + "|" + RESET);

        for (int j = 0; j < msg.length(); j++) {
            System.out.print(WHITE + "-" + RESET);
        }
        System.out.print(WHITE + "|" + RESET);
        System.out.println();
    }

    private static boolean isInteger(String s) {
        boolean value = true;
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            value = false;
        }
        return value;
    }

    private static boolean checkSingle(String input) {
        String[] var = input.split("\\s+");
        if (var.length > 1) {
            return true;
        }
        return false;
    }

    private static void fillAlbumList(List<Album> albumList) throws IOException {
        boolean check = false;
        int i = 0;
        String varInput = null;

        while (!check) {
            System.out.print("**You want to add more albums ? (Yes/No) - ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();

            if (checkSingle(line) || line.equals("")) {
                System.out.println("\n*Enter again your answer.\n");
            } else if (line.equalsIgnoreCase("no")) {
                System.out.println("\n*No more albums will be added. Exiting....\n");
                check = true;
            } else if (line.equalsIgnoreCase("yes")) {
                while (!check) {
                    System.out.print("***How many albums you want to add ? - ");
                    varInput = reader.readLine();
                    if (!isInteger(varInput) || varInput.equals("") || checkSingle(varInput)) {
                        System.out.println("\n****WRONG_INPUT - You need to enter a number again.\n");
                        continue;
                    }
                    check = true;
                }

                int numberOfAlbums = Integer.parseInt(varInput.trim());

                System.out.println("\n*Provide the albums name.");
                do {
                    System.out.print("[" + (i + 1) + "] Album name: ");
                    String input = reader.readLine().trim();
                    boolean adding = addAlbum(input, albumList);
                    if (adding) {
                        i++;
                    }
                } while (i < numberOfAlbums);
            } else {
                System.out.println("\n*You need to enter again your option.\n");
            }
        }
    }

    private static void addSongInAlbum(List<Album> albumList, String nameOfAlbum, int i) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        boolean std = false;
        while (!std) {
            System.out.print("\n[" + (i + 1) + "] " + "Name of the song: ");
            String line = input.readLine();
            if (isInteger(line) || line.equals("")) {
                System.out.println("\n**You need to enter again the name of the song.\n");
            } else {
                while (!std) {
                    System.out.print("[" + (i + 1) + "] " + "Duration of the song: ");
                    String dur = input.readLine().trim();
                    if (!isInteger(dur) || dur.equals(" ")) {
                        System.out.println("\n**You need to enter the song duration again.\n");
                        continue;
                    }
                    int timeDuration = Integer.parseInt(dur);
                    Album album = findAlbum(nameOfAlbum, albumList);
                    if (album == null) {
                        System.out.println("\n**Album was not found in the album list.\n");
                    } else {
                        album.addSongInAlbum(line, timeDuration);
                        std = true;
                    }
                }
            }
        }
    }

    private static void enterSongs(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = false;
        while (!check) {
            System.out.print("*Enter the name of the album where you want to add songs, (type q to quit) -  ");
            String line = reader.readLine();
            Album album = findAlbum(line, albumList);
            if (isInteger(line) || line.equals("")) {
                System.out.println("\n**Please enter the name of the album again.\n");
            } else if (album != null) {
                int j = 0;
                while (!check) {
                    System.out.print("*How many songs you want to enter in the album, (type q to quit) -  ");
                    String input = reader.readLine();

                    if ((input.equalsIgnoreCase("q"))) {
                        System.out.println("\n***Exiting...\n");
                        check = true;
                    } else if (!isInteger(input) || input.equals("")) {
                        System.out.println("\n**Wrong value.\n");
                    } else {
                        int number = Integer.parseInt(input);
                        while (j < number) {
                            addSongInAlbum(albumList, line, j);
                            j++;
                        }
                        check = true;
                    }
                }
                check = false;
                System.out.println();
            } else if (line.equalsIgnoreCase("q")) {
                System.out.println("\n***Exiting...\n");
                check = true;
            } else {
                System.out.println("\n*Album was not found in the albums list.\n");
            }
        }
    }

    private static void printSongs(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean var = false;
        while (!var) {
            System.out.print("*Enter your album for printing existing songs, (Type q to quit): ");
            String line = reader.readLine().trim();
            if (checkSingle(line) || line.equals(" ") || isInteger(line)) {
                System.out.println("\n*ISSUES - you need to enter the name of the album again.\n");
            } else if (line.equalsIgnoreCase("q")) {
                System.out.println("\n***Exiting...\n");
                var = true;
            } else {
                printSongsInAlbum(line, albumList);
                System.out.println();
            }
        }
    }

    private static void printPlayList(List<Song> playList) {
        Iterator<Song> irt = playList.iterator();
        System.out.println("Songs in the playList: ");
        while (irt.hasNext()) {
            System.out.println("- " + irt.next().getTitle());
        }
    }

    private static void addSongsPlayList(List<Song> playList, List<Album> albumList) throws IOException {
        ListIterator<Song> prs = playList.listIterator();
        boolean check = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!check) {
            System.out.print("*You want to add songs in the playList ? (Yes/No) - ");
            String line = reader.readLine().trim();
            if (isInteger(line) || line.equals(" ") || checkSingle(line)) {
                System.out.println("\n*You need to enter again the option.\n");
            } else if (line.equalsIgnoreCase("no")) {
                check = true;
            } else if (line.equalsIgnoreCase("yes")) {
                while (!check) {
                    System.out.print("*Enter the name of the song that you want to add, (type q to quit) - ");
                    String input = reader.readLine().trim();
                    if (isInteger(input) || input.equals(" ")) {
                        System.out.println("\n*Enter again the name of the song you want to add.\n");
                    } else if (input.equalsIgnoreCase("q")) {
                        check = true;
                    } else {
                        Song song = findSong(input, albumList);
                        if (song != null) {
                            if (!playList.isEmpty()) {
                                for (int i = 0; i < playList.size(); i++) {
                                    if (playList.get(i).equals(song)) {
                                        System.out.println("\n*Song is already in the list.\n");
                                    }
                                }
                            }
                            prs.add(song);
                        } else {
                            System.out.println("\n*Cannot find the song in the albumList.\n");
                        }
                    }
                }
            }
        }
    }

    private static Song findSongInPlaylist(List<Song> playList, String nameOfSong) {
        for (int i = 0; i < playList.size(); i++) {
            if (playList.get(i).getTitle().equalsIgnoreCase(nameOfSong)) {
                return playList.get(i);
            }
        }
        return null;
    }

    private static void options() {
        System.out.println(RED + "\n[0]" + RESET + " Quit.\n" +
                RED + "[1]" + RESET + " Add albums in the list.\n" +
                RED + "[2]" + RESET + " Add songs in albums.\n" +
                RED + "[3]" + RESET + " Remove albums from the list.\n" +
                RED + "[4]" + RESET + " Remove songs from albums.\n" +
                RED + "[5]" + RESET + " Print albums list.\n" +
                RED + "[6]" + RESET + " Print songs from album.\n" +
                RED + "[7]" + RESET + " Add song in playlist.\n" +
                RED + "[8]" + RESET + " Remove song from playlist.\n" +
                RED + "[9]" + RESET + " Print songs from the playlist.\n" +
                RED + "[10]" + RESET + " FWD/BCK/REPLAY/PLAY.\n");

    }

    private static void removeSongFromAlbum(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = false;

        while (!check) {
            System.out.print("*Enter the name of the song you want to remove, (Type q to quit) - ");
            String input = reader.readLine().trim();

            if (isInteger(input) || input.equals("")) {
                System.out.println("\n*You need to enter again the name of the song.\n");
            } else if (input.equalsIgnoreCase("q")) {
                check = true;
            } else {
                Song song = findSong(input, albumList);
                if (song != null) {
                    for (int i = 0; i < albumList.size(); i++) {
                        Album album = albumList.get(i);
                        for (int j = 0; j < album.getAlbumListOfSongs().size(); j++) {
                            if (album.returnStringFindSong(album.getAlbumListOfSongs().get(j).getTitle()).equals(song.getTitle())) {
                                album.getAlbumListOfSongs().remove(j);
                            }
                        }
                    }
                } else {
                    System.out.println("\n*Song was not found in any of the albums from the list.\n");
                }
            }
        }
    }

    private static void removeSongsFromThePlayList(List<Song> playList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ListIterator<Song> ltr = playList.listIterator();
        boolean check = false;
        while (!check) {
            System.out.print("*You need to enter the name of the song you want removed, (Type q to quit) - ");
            String input = reader.readLine().trim();

            if (isInteger(input) || input.equals("")) {
                System.out.println("\n*Enter the name of the song again.\n");
            } else if (input.equalsIgnoreCase("q")) {
                check = true;
            } else {
                Song song = findSongInPlaylist(playList, input);

                if (song != null) {
                    playList.remove(song);
                    System.out.println("\n*" + input + " was removed from the playlist.\n");
                } else {
                    System.out.println("\n*Song was not found in the playlist.\n");
                }
            }
        }
    }

    public static void playing(List<Song> playList) throws IOException {
        ArrayList<Album> albumList = new ArrayList<>();
        ListIterator<Song> iter = playList.listIterator();
        boolean forward = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;

        while (!quit) {
            intro("**iPod Player, Available Commands");
            options();

            if (playList.isEmpty()) {
                System.out.println("*No songs in the playlist at this moment.\n");
            }

            System.out.print(WHITE + "*Enter your option: " + RESET);
            String input = reader.readLine().trim();
            if (!isInteger(input) || input.equals("") || checkSingle(input)) {
                System.out.println("\n*You need to enter again your option.\n");
                continue;
            }

            int action = Integer.parseInt(input);

            if (action >= 0 && action <= 13) {
                switch (action) {
                    case 0:
                        System.out.println("\n****Exiting...");
                        quit = true;
                        break;

                    case 1:
                        System.out.println();
                        fillAlbumList(albumList);
                        break;

                    case 2:
                        System.out.println();
                        enterSongs(albumList);
                        break;

                    case 3:
                        System.out.println();
                        removeAlbumsFromTheList(albumList);
                        break;

                    case 4:
                        System.out.println();
                        removeSongFromAlbum(albumList);
                        break;

                    case 5:
                        System.out.println();
                        printAlbums(albumList);
                        break;

                    case 6:
                        System.out.println();
                        printSongs(albumList);
                        break;

                    case 7:
                        System.out.println();
                        addSongsPlayList(playList, albumList);
                        break;

                    case 8:
                        System.out.println();
                        removeSongsFromThePlayList(playList);
                        break;

                    case 9:
                        System.out.println();
                        printPlayList(playList);
                        break;

                    case 10:
                        System.out.println();
                        forwardBack(playList);
                        break;
                }
            } else {
                System.out.println("\n*Options does not exists.\n");
            }
            printLines(15);
            System.out.println();
        }
    }

    private static void forwardBack(List<Song> playList) throws IOException {
        ListIterator<Song> iter = playList.listIterator();
        boolean forward = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;

        while (!quit) {
            System.out.print("*Enter your action, (FWD,BCK,REPLAY,PLAY, q to quit) - ");
            String line = reader.readLine().trim();
            if (isInteger(line) || line.equals("")) {
                System.out.println("\n*Enter your option again.\n");
            } else if (playList.isEmpty()) {
                System.out.println("\n*PlayList is empty, no songs.\n");
            } else if (line.equalsIgnoreCase("q")) {
                System.out.println("\n*Exiting...\n");
                quit = true;
            } else {
                if (line.equalsIgnoreCase("FWD")) {
                    System.out.println();
                    if (!forward) {
                        if (iter.hasNext()) {
                            iter.next();
                        }
                        forward = true;
                    }

                    if (iter.hasNext()) {
                        System.out.println("\nNow playing: " + iter.next().toString() + "\n");
                    } else {
                        System.out.println("\n*We are at the end of the playlist.\n");
                        forward = false;
                    }

                } else if (line.equalsIgnoreCase("BCK")) {
                    System.out.println();
                    if (forward) {
                        if (iter.hasPrevious()) {
                            iter.previous();
                        }
                        forward = false;
                    }
                    if (iter.hasPrevious()) {
                        System.out.println("\nNow playing: " + iter.previous().toString() + "\n");
                    } else {
                        System.out.println("\n*We are at the start of the playlist.\n");
                        forward = false;
                    }
                } else if (line.equalsIgnoreCase("REPLAY")) {
                    if (forward) {
                        if (iter.hasPrevious()) {
                            System.out.println("\nNow replaying: " + iter.previous().toString() + "\n");
                            forward = false;
                        } else {
                            System.out.println("\n*We are at the start of the list\n");
                        }
                    } else {
                        if (iter.hasNext()) {
                            System.out.println("\nNow replaying: " + iter.next().toString() + "\n");
                            forward = true;
                        }
                    }
                } else if (line.equalsIgnoreCase("PLAY")) {
                    System.out.println();
                    System.out.println("\n*Now playing: " + iter.next().toString() + "\n");
                } else {
                    System.out.println("\n*You need to enter the value again.\n");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        playing(new LinkedList<Song>());
    }
}

