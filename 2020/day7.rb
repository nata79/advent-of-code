def parse_child(child)
  {
    count: child.match(/(\d+)\s(.+)\s/)[1].to_i,
    colour: child.match(/(\d+)\s(.+)\s/)[2]
  }
end

rules = File.read('day7.txt').split(".\n").map do |rule|
  {
    colour: rule.split(' contain ')[0].slice(0, rule.split(' contain ')[0].size - 5),
    children: rule.split(' contain ')[1].split(',').filter { |child| child.match?(/(\d+)\s(.+)\s/) }.map { |child| parse_child(child) }
  }
end

def has_colour(rule, colour, rules)
  return false if rule.nil?
  return false if rule[:children].empty?
  return true if rule[:children].any? { |children| children[:colour] == colour }

  rule[:children].any? do |child|
    r = rules.find { |r| r[:colour] == child[:colour] }
    has_colour(r, colour, rules)
  end
end

accepted_colours = rules.filter do |rule|
  has_colour(rule, 'shiny gold', rules)
end

p accepted_colours.size

def bag_count(rule, rules)
  rule[:children].map { |child| child[:count] + (child[:count] * bag_count(rules.find { |r| r[:colour] == child[:colour] }, rules)) }.sum
end

p bag_count(rules.find { |r| r[:colour] == 'shiny gold' }, rules)