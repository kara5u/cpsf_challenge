/******************************
 * 
 * ls.c
 *
 * the own implement ls command
 * author: Yutaka Karatsu (karasu@ht.sfc.keio.ac.jp)
 * date: 2011.10.15
 *
 ******************************/


#include <stdio.h>
#include <stdlib.h>
#include <dirent.h> // DIR, opendir(), readdir()

int main(int argc, char* argv);


/*-----------------------------
 main method
 ------------------------------*/
int main(int argc, char* argv) {
    char* dirpath = NULL;
    DIR* directory_p = NULL;
    struct dirent* dir_entry = NULL;

    dirpath = getenv("PWD"); // get the absolute path of current directory
    directory_p = opendir(dirpath);
    if (directory_p == NULL) {
        perror("open specified directory");
        exit(EXIT_FAILURE);
    }

    while ( (dir_entry = readdir(directory_p)) != NULL ) {
        printf("%s\t", dir_entry->d_name);
    }
    printf("\n");

    exit(EXIT_SUCCESS);
}

