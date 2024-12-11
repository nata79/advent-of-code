grid = File.read('day10.txt').split(/\n/).map { _1.split('').map(&:to_i) }

starting_points = []

0.upto(grid.size - 1) do |i|
  0.upto(grid[i].size - 1) do |j|
    starting_points << [i, j] if grid[i][j] == 0
  end
end

total = 0

def find_highest_points(grid, path)
  current_position = path.last
  return [current_position] if grid[current_position[0]][current_position[1]] == 9


  highest_points = []
  
  [[0, 1], [1, 0], [0, -1], [-1, 0]].each do |direction|
    new_position = [current_position[0] + direction[0], current_position[1] + direction[1]]

    next if new_position[0] < 0 || new_position[1] < 0 || new_position[0] >= grid.size || new_position[1] >= grid[0].size
    next if path.include?(new_position)
    next if grid[new_position[0]][new_position[1]] - grid[current_position[0]][current_position[1]] != 1

    highest_points += find_highest_points(grid, path + [new_position])
  end

  highest_points
end

total = 0

starting_points.each do |point|
  total += find_highest_points(grid, [point]).uniq.size
end

p total

def find_paths(grid, path)
  current_position = path.last
  return 1 if grid[current_position[0]][current_position[1]] == 9


  paths = 0
  
  [[0, 1], [1, 0], [0, -1], [-1, 0]].each do |direction|
    new_position = [current_position[0] + direction[0], current_position[1] + direction[1]]

    next if new_position[0] < 0 || new_position[1] < 0 || new_position[0] >= grid.size || new_position[1] >= grid[0].size
    next if path.include?(new_position)
    next if grid[new_position[0]][new_position[1]] - grid[current_position[0]][current_position[1]] != 1

    paths += find_paths(grid, path + [new_position])
  end

  paths
end

total = 0

starting_points.each do |point|
  total += find_paths(grid, [point])
end

p total