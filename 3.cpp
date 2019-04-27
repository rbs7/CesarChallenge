/*
  3.Array and Strings
  
  Time complexity: O(m*n)
  Space complexity: O(m*n)
*/
#include <bits/stdc++.h>

using namespace std;

int countTypos (char* w1, int i, char* w2, int j) {
  //printf("%d %d %s %s\n", i, j, w1+i, w2+j);
  if (w1[i] == ',' || w1[i] == '\0') {
    if (w2[j] == ',' || w2[j] == '\0') {
      return 0;
    } else {
      return 1 + countTypos(w1, i, w2, j+1);
    }
  } else {
    if (w2[j] == ',' || w2[j] == '\0') {
      return 1 + countTypos(w1, i+1, w2, j);
    } else {
      if (w1[i]==w2[j]) {
        return countTypos(w1, i+1, w2, j+1);
      } else {
        return 1 + min(countTypos(w1, i+1, w2, j+1), min(countTypos(w1, i+1, w2, j), countTypos(w1, i, w2, j+1)));
      }
    }
  }
}

int main () {
  char w1[2001], w2[2002];
  
  while (scanf(" %s %s", w1, w2) != EOF) {
    if (countTypos(w1, 0, w2, 0) > 1) printf("false\n");
    else printf("true\n");
  }

  return 0;
}
