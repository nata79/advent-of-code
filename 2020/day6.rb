groups = File.read('day6.txt').split("\n\n").map { |group| group.split("\n").map(&:chars) }

p groups.map { |group| group.flatten.uniq.size }.sum

p groups.map { |group| group.reduce { |acc, answers| acc & answers }.size }.sum
