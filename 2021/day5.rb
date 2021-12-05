require 'matrix'

input = "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"

class Point
  attr_accessor :x
  attr_accessor :y
  
  def initialize(x, y)
    @x = x
    @y = y
  end

  def inspect
    to_s
  end

  def to_s
    "#{x},#{y}"
  end

  def ==(other_object)
    x == other_object.x && y == other_object.y
  end

  def eql?(other)
    x == other.x && y == other.y
  end

  def hash
    x.hash * y.hash
  end
end

class Line
  attr_accessor :from
  attr_accessor :to
  
  def initialize(from, to)
    @from = from
    @to = to
  end

  def diagonal?
    from.x != to.x && from.y != to.y
  end

  def points
    vector = Vector[to.x - from.x, to.y - from.y].normalize.round
    if vector[0] % 1 == 0 && vector[1] % 1 == 0      
      current = Point.new(from.x, from.y)
      list = [current]

      while current != to
        current = Point.new(current.x + vector[0].ceil.to_i, current.y + vector[1].ceil.to_i)
        list << current
      end

      list
    end
  end

  def inspect
    to_s
  end

  def to_s
    "#{from} -> #{to}"
  end
end

lines = []

input = File.read('day5.txt')

input.split("\n").map do |line|
  match = /(\d+),(\d+) -> (\d+),(\d+)/.match(line)
  lines << Line.new(Point.new(match[1].to_i, match[2].to_i), Point.new(match[3].to_i, match[4].to_i))
end

p lines.map(&:points).compact.flatten.tally.filter { |k, v| v > 1 }.size