table = []

File.read('day4.txt').split("\n").each do |line|
  row = []
  line.chars.each do |char|
    row << char
  end
  table << row
end

word = ['X', 'M', 'A', 'S']

total = 0

directions = [[1, 0], [-1, 0], [0, 1], [0, -1], [1, 1], [-1, -1], [1, -1], [-1, 1]]

def look(table, direction, word, x, y)
  return true if word.empty?
  return false if x < 0 || y < 0 || x >= table.size || y >= table[x].size
  return false if table[x][y] != word[0]

  new_word = word[1..-1]
  
  look(table, direction, new_word, x + direction[0], y + direction[1])  
end

table.each_with_index do |row, i|
  row.each_with_index do |char, j|
    directions.each do |direction|
      total += 1 if look(table, direction, word, i, j)
    end
  end
end

p total

total = 0

table.each_with_index do |row, i|
  row.each_with_index do |char, j|
    if char == 'A'
      top_left = i > 0 && j > 0 ? table[i - 1][j - 1] : nil
      top_right = i > 0 && j < row.size - 1 ? table[i - 1][j + 1] : nil
      bottom_left = i < table.size - 1 && j > 0 ? table[i + 1][j - 1] : nil
      bottom_right = i < table.size - 1 && j < row.size - 1 ? table[i + 1][j + 1] : nil

      total += 1 if ['MAS', 'SAM'].include?([top_left, char, bottom_right].join('')) && ['MAS', 'SAM'].include?([top_right, char, bottom_left].join(''))
    end
  end
end

p total