/*
  2.Array and Strings
  
  Time complexity: O(max(m,n))
  Space complexity: O(max(m,n))
*/
#include <bits/stdc++.h>

using namespace std;

int isPartialPermutation (char* w1, char* w2) {
  int ok = 1, i, cont;
  int alpha[26] = {0};
  
  if (w1[0] != w2[0]) { //first letter check
    ok=0;
  }
  if (ok) {
    for (i = 0, cont = 0; w1[i] != '\0' && w2[i] != '\0' && w1[i] != ','; i++) {
      if (w1[i] != w2[i]) { //letter by letter check
        cont++;
        alpha[tolower(w1[i])-'a']++;
        alpha[tolower(w2[i])-'a']--;
      }
    }
    if (w1[i] == ',') { //comma removal
      w1[i] = '\0';
    }
    if (w1[i] != '\0' || w2[i] != '\0') { //string length check
      ok = 0;
    }
    if (i > 3 && cont > i*2/3) {
      ok = 0;
    }
    for (i = 0; i < 26; i++) {  //letter by letter check
      if (alpha[i] != 0) {
        ok = 0;
      }
    }
  }
  
  return ok;
}

int main () {
  char w1[2001], w2[2002];
  
  while (scanf(" %s %s", w1, w2) != EOF) {
    if (isPartialPermutation(w1, w2)) printf("true\n");
    else printf("false\n");
  }

  return 0;
}
