/*
  1.Array and Strings
  
  Time complexity: O(n)
  Space complexity: O(n)
*/
#include <bits/stdc++.h>

using namespace std;

void spaceTo32 (char *arr, int len) {
  int i, spaces;
  
  for (i = 0, spaces = 0; i < len; i++) {
    if (arr[i] == ' ') {
      spaces++;
    }
  }
  
  arr[len + spaces*2] = '\0';
  for (i = len-1; i >= 0; i--) {
    if (arr[i] != ' ') {
      arr[i + spaces*2] = arr[i];
    } else {
      arr[i + spaces*2] = '2';
      arr[i + spaces*2 - 1] = '3';
      arr[i + spaces*2 - 2] = '&';
      spaces--;
    }
  }
}

int main () {
  char arr[2001];
  int len;
  
  scanf(" \"%[^\"]\", %d", arr, &len);
  //printf("%s\n", arr);
  //printf("%d\n", len);
  
  spaceTo32(arr, len);
  
  printf("\"%s\"\n", arr);

  return 0;
}
