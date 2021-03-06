package basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.*;

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
            out.println("\n**WRONG_INPUT - you need to enter again the name of the album.\n");
            return false;
        } else {
            Album album = findAlbum(albumName, albumList);
            if (album != null) {
                out.println("\n**Album already in the list.\n");
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
            out.println("\n*Mentioned album is not in the list.\n");
        }
        return var;
    }

    private static void removeAlbumsFromTheList(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean check = false;
        while (!check) {
            out.print("*Enter the name of the album you want to remove: ");
            String input = reader.readLine().trim();
            if (isInteger(input) || input.equals("")) {
                out.println("\n*You need to enter the name of the album again.\n");
            } else {
                removeAlbum(input, albumList);
                check = true;
            }
        }
    }

    private static void printAlbums(List<Album> albumList) {
        out.print("*We have the following albums available for us: \n");
        if (albumList.isEmpty()) {
            out.println("\n*No albums in albums list.\n");
        } else {
            for (int i = 0; i < albumList.size(); i++) {
                out.println("- " + albumList.get(i).getNameOfAlbum());
            }
        }
    }

    private static void printSongsInAlbum(String nameOfAlbum, List<Album> albumList) {
        Album album = findAlbum(nameOfAlbum, albumList);
        if (album != null) {
            out.print("\nAlbum: " + album.getNameOfAlbum() + "\nSongs:\n");
            album.printSongs();
        } else {
            out.println("\n*Issues - cannot find the given album in the list.");
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
            out.print("--");
        }
        out.println();
    }

    private static void intro(String msg) {
        out.print(WHITE + "|" + RESET);
        for (int i = 0; i < msg.length(); i++) {
            out.print(WHITE + "-" + RESET);
        }
        out.print(WHITE + "|" + RESET);
        out.println("\n" + WHITE + "|" + RESET + RED_BOLD + msg + RESET + WHITE + "|" + RESET);
        out.print(WHITE + "|" + RESET);

        for (int j = 0; j < msg.length(); j++) {
            out.print(WHITE + "-" + RESET);
        }
        out.print(WHITE + "|" + RESET);
        out.println();
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
            out.print("**You want to add more albums ? (Yes/No) - ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();

            if (checkSingle(line) || line.equals("")) {
                out.println("\n*Enter again your answer.\n");
            } else if (line.equalsIgnoreCase("no")) {
                out.println("\n*No more albums will be added. Exiting....\n");
                check = true;
            } else if (line.equalsIgnoreCase("yes")) {
                while (!check) {
                    out.print("***How many albums you want to add ? - ");
                    varInput = reader.readLine();
                    if (!isInteger(varInput) || varInput.equals("") || checkSingle(varInput)) {
                        out.println("\n****WRONG_INPUT - You need to enter a number again.\n");
                        continue;
                    }
                    check = true;
                }

                int numberOfAlbums = Integer.parseInt(varInput.trim());

                out.println("\n*Provide the albums name.");
                do {
                    out.print("[" + (i + 1) + "] Album name: ");
                    String input = reader.readLine().trim();
                    boolean adding = addAlbum(input, albumList);
                    if (adding) {
                        i++;
                    }
                } while (i < numberOfAlbums);
            } else {
                out.println("\n*You need to enter again your option.\n");
            }
        }
    }

    private static void addSongInAlbum(List<Album> albumList, String nameOfAlbum, int i) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        boolean std = false;
        while (!std) {
            out.print("\n[" + (i + 1) + "] " + "Name of the song: ");
            String line = input.readLine();
            if (isInteger(line) || line.equals("")) {
                out.println("\n**You need to enter again the name of the song.\n");
            } else {
                while (!std) {
                    out.print("[" + (i + 1) + "] " + "Duration of the song: ");
                    String dur = input.readLine().trim();
                    if (!isInteger(dur) || dur.equals(" ")) {
                        out.println("\n**You need to enter the song duration again.\n");
                        continue;
                    }
                    int timeDuration = Integer.parseInt(dur);
                    Album album = findAlbum(nameOfAlbum, albumList);
                    if (album == null) {
                        out.println("\n**Album was not found in the album list.\n");
                    } else {
                        album.addSongInAlbum(line, timeDuration);
                        std = true;
                    }
                }
            }
        }
    }

    private static void enterSongs(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean check = false;
        while (!check) {
            out.print("*Enter the name of the album where you want to add songs, (type q to quit) -  ");
            String line = reader.readLine();
            Album album = findAlbum(line, albumList);
            if (isInteger(line) || line.equals("")) {
                out.println("\n**Please enter the name of the album again.\n");
            } else if (album != null) {
                int j = 0;
                while (!check) {
                    out.print("*How many songs you want to enter in the album, (type q to quit) -  ");
                    String input = reader.readLine();

                    if ((input.equalsIgnoreCase("q"))) {
                        out.println("\n***Exiting...\n");
                        check = true;
                    } else if (!isInteger(input) || input.equals("")) {
                        out.println("\n**Wrong value.\n");
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
                out.println();
            } else if (line.equalsIgnoreCase("q")) {
                out.println("\n***Exiting...\n");
                check = true;
            } else {
                out.println("\n*Album was not found in the albums list.\n");
            }
        }
    }

    private static void printSongs(List<Album> albumList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean var = false;
        while (!var) {
            out.print("*Enter your album for printing existing songs, (Type q to quit): ");
            String line = reader.readLine().trim();
            if (checkSingle(line) || line.equals(" ") || isInteger(line)) {
                out.println("\n*ISSUES - you need to enter the name of the album again.\n");
            } else if (line.equalsIgnoreCase("q")) {
                out.println("\n***Exiting...\n");
                var = true;
            } else {
                printSongsInAlbum(line, albumList);
                out.println();
            }
        }
    }

    private static void printPlayList(List<Song> playList) {
        Iterator<Song> irt = playList.iterator();
        out.println("Songs in the playList: ");
        while (irt.hasNext()) {
            out.println("- " + irt.next().getTitle());
        }
    }

    private static void addSongsPlayList(List<Song> playList, List<Album> albumList) throws IOException {
        ListIterator<Song> prs = playList.listIterator();
        boolean check = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        while (!check) {
            out.print("*You want to add songs in the playList ? (Yes/No) - ");
            String line = reader.readLine().trim();
            if (isInteger(line) || line.equals(" ") || checkSingle(line)) {
                out.println("\n*You need to enter again the option.\n");
            } else if (line.equalsIgnoreCase("no")) {
                check = true;
            } else if (line.equalsIgnoreCase("yes")) {
                while (!check) {
                    out.print("*Enter the name of the song that you want to add, (type q to quit) - ");
                    String input = reader.readLine().trim();
                    if (isInteger(input) || input.equals(" ")) {
                        out.println("\n*Enter again the name of the song you want to add.\n");
                    } else if (input.equalsIgnoreCase("q")) {
                        check = true;
                    } else {
                        Song song = findSong(input, albumList);
                        if (song != null) {
                            if (!playList.isEmpty()) {
                                for (int i = 0; i < playList.size(); i++) {
                                    if (playList.get(i).equals(song)) {
                                        out.println("\n*Song is already in the list.\n");
                                    }
                                }
                            }
                            prs.add(song);
                        } else {
                            out.println("\n*Cannot find the song in the albumList.\n");
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
        out.println(RED + "\n[0]" + RESET + " Quit.\n" +
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean check = false;

        while (!check) {
            out.print("*Enter the name of the song you want to remove, (Type q to quit) - ");
            String input = reader.readLine().trim();

            if (isInteger(input) || input.equals("")) {
                out.println("\n*You need to enter again the name of the song.\n");
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
                    out.println("\n*Song was not found in any of the albums from the list.\n");
                }
            }
        }
    }

    private static void removeSongsFromThePlayList(List<Song> playList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        ListIterator<Song> ltr = playList.listIterator();
        boolean check = false;
        while (!check) {
            out.print("*You need to enter the name of the song you want removed, (Type q to quit) - ");
            String input = reader.readLine().trim();

            if (isInteger(input) || input.equals("")) {
                out.println("\n*Enter the name of the song again.\n");
            } else if (input.equalsIgnoreCase("q")) {
                check = true;
            } else {
                Song song = findSongInPlaylist(playList, input);

                if (song != null) {
                    playList.remove(song);
                    out.println("\n*" + input + " was removed from the playlist.\n");
                } else {
                    out.println("\n*Song was not found in the playlist.\n");
                }
            }
        }
    }

    public static void playing(List<Song> playList) throws IOException {
        ArrayList<Album> albumList = new ArrayList<>();
        ListIterator<Song> iter = playList.listIterator();
        boolean forward = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean quit = false;

        while (!quit) {
            intro("**iPod Player, Available Commands");
            options();

            if (playList.isEmpty()) {
                out.println("*No songs in the playlist at this moment.\n");
            }

            out.print(WHITE + "*Enter your option: " + RESET);
            String input = reader.readLine().trim();
            if (!isInteger(input) || input.equals("") || checkSingle(input)) {
                out.println("\n*You need to enter again your option.\n");
                continue;
            }

            int action = Integer.parseInt(input);

            if (action >= 0 && action <= 13) {
                switch (action) {
                    case 0:
                        out.println("\n****Exiting...");
                        quit = true;
                        break;

                    case 1:
                        out.println();
                        fillAlbumList(albumList);
                        break;

                    case 2:
                        out.println();
                        enterSongs(albumList);
                        break;

                    case 3:
                        out.println();
                        removeAlbumsFromTheList(albumList);
                        break;

                    case 4:
                        out.println();
                        removeSongFromAlbum(albumList);
                        break;

                    case 5:
                        out.println();
                        printAlbums(albumList);
                        break;

                    case 6:
                        out.println();
                        printSongs(albumList);
                        break;

                    case 7:
                        out.println();
                        addSongsPlayList(playList, albumList);
                        break;

                    case 8:
                        out.println();
                        removeSongsFromThePlayList(playList);
                        break;

                    case 9:
                        out.println();
                        printPlayList(playList);
                        break;

                    case 10:
                        out.println();
                        forwardBack(playList);
                        break;
                }
            } else {
                out.println("\n*Options does not exists.\n");
            }
            out.println();
        }
    }

    private static void forwardBack(List<Song> playList) throws IOException {
        ListIterator<Song> iter = playList.listIterator();
        boolean forward = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean quit = false;

        while (!quit) {
            out.print("*Enter your action, (FWD,BCK,REPLAY,PLAY, q to quit) - ");
            String line = reader.readLine().trim();
            if (isInteger(line) || line.equals("")) {
                out.println("\n*Enter your option again.\n");
            } else if (line.equalsIgnoreCase("q")) {
                out.println("\n*Exiting...\n");
                quit = true;
            } else if (playList.isEmpty()) {
                out.println("\n*PlayList is empty, no songs.\n");
            } else {
                if (line.equalsIgnoreCase("FWD")) {
                    out.println();
                    if (!forward) {
                        if (iter.hasNext()) {
                            iter.next();
                        }
                        forward = true;
                    }

                    if (iter.hasNext()) {
                        out.println("\nNow playing: " + iter.next().toString() + "\n");
                    } else {
                        out.println("\n*We are at the end of the playlist.\n");
                        forward = false;
                    }

                } else if (line.equalsIgnoreCase("BCK")) {
                    out.println();
                    if (forward) {
                        if (iter.hasPrevious()) {
                            iter.previous();
                        }
                        forward = false;
                    }
                    if (iter.hasPrevious()) {
                        out.println("\nNow playing: " + iter.previous().toString() + "\n");
                    } else {
                        out.println("\n*We are at the start of the playlist.\n");
                        forward = false;
                    }
                } else if (line.equalsIgnoreCase("REPLAY")) {
                    if (forward) {
                        if (iter.hasPrevious()) {
                            out.println("\nNow replaying: " + iter.previous().toString() + "\n");
                            forward = false;
                        } else {
                            out.println("\n*We are at the start of the list\n");
                        }
                    } else {
                        if (iter.hasNext()) {
                            out.println("\nNow replaying: " + iter.next().toString() + "\n");
                            forward = true;
                        }
                    }
                } else if (line.equalsIgnoreCase("PLAY")) {
                    out.println();
                    out.println("\n*Now playing: " + iter.next().toString() + "\n");
                } else {
                    out.println("\n*You need to enter the value again.\n");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        playing(new LinkedList<Song>());
    }
}