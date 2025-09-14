#!/bin/bash
set +H

BASE_URL="http://localhost:8080/api/posts"

echo "=== 1️⃣ Получаем все посты (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 2️⃣ Создаём новый пост ==="
RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{"id":0,"content":"Hello"}')
echo "Ответ сервера при создании: $RESPONSE"

# Извлекаем ID поста
POST_ID=$(echo $RESPONSE | grep -o '"id":[0-9]*' | sed 's/"id"://')
echo "Создан пост с ID = $POST_ID"
echo -e "\n"

echo "=== 3️⃣ Получаем все посты после создания ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 4️⃣ Получаем пост по ID ==="
curl -s $BASE_URL/$POST_ID
echo -e "\n"

echo "=== 5️⃣ Обновляем пост ==="
RESPONSE=$(curl -s -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d "{\"id\":$POST_ID,\"content\":\"Updated content\"}")
echo "Ответ сервера при обновлении: $RESPONSE"
echo -e "\n"

echo "=== 6️⃣ Получаем пост после обновления ==="
curl -s $BASE_URL/$POST_ID
echo -e "\n"

echo "=== 7️⃣ Удаляем пост ==="
curl -s -X DELETE $BASE_URL/$POST_ID
echo "Пост с ID = $POST_ID удалён"
echo -e "\n"

echo "=== 8️⃣ Получаем все посты после удаления (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"
