require 'set'

input = File.read('day8.txt').split(/\n/)

max_x = input[0].size
max_y = input.size

antenas = Hash.new([])

input.each_with_index do |line, y|
  line.split('').each_with_index do |char, x|
    if char != '.'
      antenas[char] += [[x, y]]
    end
  end
end

antinodes = []

antenas.each do |antena, positions|
  positions.combination(2).each do |pos1, pos2|
    next unless pos1 && pos2

    vector = [pos1[0] - pos2[0], pos1[1] - pos2[1]]
    maybe_antinode1 = [pos1[0] + vector[0], pos1[1] + vector[1]]

    vector = [pos2[0] - pos1[0], pos2[1] - pos1[1]]
    maybe_antinode2 = [pos2[0] + vector[0], pos2[1] + vector[1]]

    if maybe_antinode1[0].between?(0, max_x-1) && maybe_antinode1[1].between?(0, max_y-1)
      antinodes << maybe_antinode1
    end

    if maybe_antinode2[0].between?(0, max_x-1) && maybe_antinode2[1].between?(0, max_y-1)
      antinodes << maybe_antinode2
    end
  end
end

p antinodes.uniq.size

antinodes = []

def add_antinodes_until_out_of_map(pos, vector, antinodes, max_x, max_y)
  while true
    maybe_antinode = [pos[0] + vector[0], pos[1] + vector[1]]
    break unless maybe_antinode[0].between?(0, max_x-1) && maybe_antinode[1].between?(0, max_y-1)
    pos = maybe_antinode
    antinodes << maybe_antinode
  end
end

antenas.each do |antena, positions|
  positions.combination(2).each do |pos1, pos2|
    next unless pos1 && pos2

    vector = [pos1[0] - pos2[0], pos1[1] - pos2[1]]
    add_antinodes_until_out_of_map(pos1, vector, antinodes, max_x, max_y)
    add_antinodes_until_out_of_map(pos1, [-vector[0], -vector[1]], antinodes, max_x, max_y)

    vector = [pos2[0] - pos1[0], pos2[1] - pos1[1]]
    add_antinodes_until_out_of_map(pos2, vector, antinodes, max_x, max_y)
    add_antinodes_until_out_of_map(pos2, [-vector[0], -vector[1]], antinodes, max_x, max_y)
  end
end

p antinodes.uniq.size