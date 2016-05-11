103062205 劉凌君 103062242 陳百榆 assignment6

有多加音樂

一開始setup先初始化，裡面包括角色，按鈕，音樂，network初始，還有要把資料匯進來。

draw裡把network先畫出來，再畫標題，所有角色。特別注意的是當滑鼠碰到角色時，會顯現名字，
是先用一個for迴圈去跑，檢查滑鼠的xy是否在角色的範圍內，如果是會畫出名字。

mouseDragged裡只要此時滑鼠有點到某個node，那個node就會跟著mouse，所以在character裡寫一
個function可以改變node的位置，並將滑鼠的xy傳進去。

mousePressed此時如果在某個node的區域裡，ch_now就會等於那個node。ch_now是指滑鼠正抓著的node。
用一個for迴圈去跑檢查mouse是在哪個node的範圍裡。

mousereleased如果滑鼠正抓著一個node，這時分成三種情況，第一個是node不在network裡，然而滑鼠正
抓著node into the big circle，此時network裡面要增加node而node要分配位置。第二個是node在
network裡，然而滑鼠正抓著node out of the big circle，此時network裡面要移除node而node要回去他
原本的位置，呼叫setlocal。

buttonA add all
先將network裡的所有清掉，再將全部角色加進去。

buttonB clear
將network裡的所有清掉。

keyPressed
按一次鍵盤上的按鍵，會換集數。並且全部會在初始化一遍。