/*
  7.Linked Lists
  
  Time complexity: O(n)
  Space complexity: O(n)
*/
#include <bits/stdc++.h>

using namespace std;

vector <pair <char,int> > g;  //Each pair is a node of a linked list (value, next)
vector <int> marked;

int go (int node) {
  if (node == -1) return -1;
  if (marked[node]) return node;
  marked[node] = 1;
  return go(g[node].second);
}

int main () {
  int a, b;
  int ans;
  
  //Graph definition
  g.push_back(make_pair('c', 1)); //0
  g.push_back(make_pair('a', 2)); //1
  g.push_back(make_pair('e', 3)); //2
  g.push_back(make_pair('h', 4)); //3
  g.push_back(make_pair('j', 5)); //4
  g.push_back(make_pair('b', 6)); //5
  g.push_back(make_pair('a', -1));//6
  g.push_back(make_pair('d', 8)); //7
  g.push_back(make_pair('f', 4)); //8
  
  //Query definition
  a = 0;
  b = 7;

  marked.assign(g.size(), 0);
  go(a);
  ans = go(b);
  if (ans == -1) printf("No intersecting node found\n");
  else printf("Interection in %c\n", g[ans].first);
  

  return 0;
}
