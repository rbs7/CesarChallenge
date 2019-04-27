/*
  5.Linked Lists
  
  Time complexity: O(n*log(n))
  Space complexity: O(n)
*/
#include <bits/stdc++.h>

using namespace std;

vector <pair <char,int> > g;  //Each pair is a node of a linked list (value, next)
set <char> marked;

void removeDuplicates (int node) {
  if (node == -1) return;
  if (marked.find(g[node].first) == marked.end()) {
    //printf("%c %d ok\n", g[node].first, g[node].second);
    marked.insert(g[node].first);
    removeDuplicates(g[node].second); //next
  } else { //duplicated
    //printf("%c %d dup\n", g[node].first, g[node].second);
    g[node] = g[g[node].second];
    removeDuplicates(node);
  }
}

void printLinkedList(int node) {
  if (node == -1) return;
  printf("%c\n", g[node].first);
  printLinkedList(g[node].second);
}

int main () {
  char c;
  while(scanf(" %c", &c) != EOF) {
    g.push_back(make_pair(c, -1));
    if (g.size() > 1) {
      g[g.size()-2].second = g.size()-1;
    }
  }
  
  removeDuplicates(0);
  printLinkedList(0);
  

  return 0;
}
