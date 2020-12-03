map = File.read('day3.txt').split("\n").map(&:chars).map { |line| line * 100 }

slopes = [
  [1, 1],
  [3, 1],
  [5, 1],
  [7, 1],
  [1, 2]
]

result = slopes.map do |slope|
  trees = 0

  x = 0
  y = 0

  while y < map.size
    trees += 1 if map[y][x] == '#'
    x += slope[0]
    y += slope[1]
  end

  trees
end.reduce { |acc, res| acc * res }

p result
