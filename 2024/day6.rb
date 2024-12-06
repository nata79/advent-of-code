require 'set'

directions = ['^', '>', 'v', '<']
obstacles = []
initial_position = [0, 0]
initial_direction = '^'
input_lines = File.read('day6.txt').split("\n")
max_x = input_lines.first.size
max_y = input_lines.size

input_lines.each_with_index do |line, y|
  line.split('').each_with_index do |a, x|
    if a == '#'
      obstacles << [x, y]
    elsif directions.include?(a)
      initial_position = [x, y]
      initial_direction = a
    end
  end
end

def next_step(position, direction)
  position = position.dup
  case direction
  when '^'
    position[1] -= 1
  when '>'
    position[0] += 1
  when 'v'
    position[1] += 1
  when '<'
    position[0] -= 1
  end
  position
end

def move(position, direction, obstacles, directions)
  next_position = next_step(position, direction)
  
  while obstacles.include?(next_position)
    direction = directions[(directions.index(direction) + 1) % 4]
    next_position = next_step(position, direction)
  end

  [next_position, direction]
end

def in_a_loop(position, direction, obstacles, directions, max_x, max_y, visited)
  while position[0] >= 0 && position[0] < max_x && position[1] >= 0 && position[1] < max_y
    position, direction = move(position, direction, obstacles, directions)
    return true if visited.include?([position, direction])
    visited << [position, direction]
  end
  
  false
end

steps = []

current_position = initial_position
current_direction = initial_direction

while current_position[0] >= 0 && current_position[0] < max_x && current_position[1] >= 0 && current_position[1] < max_y
  steps << [current_position, current_direction]
  current_position, current_direction = move(current_position, current_direction, obstacles, directions)
end

unique_locations = steps.map { _1[0] }.uniq
p unique_locations.size

total = 0

tried = Set.new

steps.each_with_index do |step, i|
  p "#{i}/#{steps.size-1}"

  visited = steps[0...i].to_set

  if i < steps.size - 1 && steps[i+1][0] != initial_position
    next if tried.include?(steps[i+1][0])
    total += 1 if in_a_loop(step[0], step[1], obstacles + [steps[i+1][0]], directions, max_x, max_y, visited)
    tried << steps[i+1][0]
  end
end

p total