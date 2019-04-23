package spell;

public class Words implements ITrie
{
    private int wordNum;
    private int nodeNum;
    private WordNode root;
    public Words()
    {
        wordNum = 0;
        nodeNum = 1;
        root = new WordNode();
    }
    public void add(String word)
    {
        String input = word.toLowerCase();
        WordNode curNode = root;    // start with root
        for (int i = 0; i < input.length(); i++)
        {
            int letter = input.charAt(i);
            int position = letter - 'a';
            if (curNode.getNodeArray()[position] == null) // check current node's 26 next link
            {
                curNode.getNodeArray()[position] = new WordNode(); // create new link if goes this path
                nodeNum++;
            }
            if (i == (input.length() - 1))  // if in last char of the sentence
            {
                if (curNode.getNodeArray()[position].getValue() == 0)
                {
                    wordNum++;
                }
                curNode.getNodeArray()[position].increFre();
            }
            curNode = curNode.getNodeArray()[position];
        }
    }
    public ITrie.INode find(String word)
    {
        String input = word.toLowerCase();
        WordNode curNode = root;
        for (int i = 0; i < input.length(); i++)
        {
            int letter = input.charAt(i);
            int position = letter - 'a';
            if (curNode.getNodeArray()[position] == null)   // if the desire path is null, can't found
            {
                return null;
            }
            if (i == input.length() - 1) // condition where it is last char
            {
                if (curNode.getNodeArray()[position].getValue() > 0) //if higher fre, use it or it's alphabet order
                {
                    return curNode.getNodeArray()[position];
                }
                else
                {
                    return null;
                }
            }
            curNode = curNode.getNodeArray()[position];  //update the current node
        }
        return null;
    }
    public int getWordCount()
    {
        return wordNum;
    }
    public int getNodeCount()
    {
        return nodeNum;
    }
    @Override
    public String toString()
    {
        StringBuffer total = new StringBuffer();
        StringBuffer temp = new StringBuffer();
        curString(root, total, temp);
        return total.toString();
    }
    private void curString(WordNode node, StringBuffer total, StringBuffer temp)
    {
        for (int i = 'a'; i <= 'z'; i++)
        {
            if (node.getNodeArray()[i - 'a'] != null) //check each valid node, and append it to temp string
            {
                char letter = (char)i;
                temp.append(letter);
                if (node.getNodeArray()[i - 'a'].getValue() > 0) // if it has frequency, it's a word
                {
                    total.append(temp.toString());
                    total.append("\n");
                }
                curString(node.getNodeArray()[i - 'a'],total,temp);
                temp.deleteCharAt(temp.length() - 1); // come back out recursive, need to remove last append
            }
        }
    }
    @Override
    public int hashCode()
    {
        return 3 * wordNum + nodeNum;
    }
    @Override
    public boolean equals(Object o)
    {
        if (o == null || this.getClass() != o.getClass()) // check if same class and if null pointer
        {
            return false;
        }
        if (o == this)  // identical memory location check
        {
            return true;
        }
        Words check = (Words)o;     // since same class, cast from object to Words class
        if (this.getWordCount() != check.getWordCount() || this.getNodeCount() != check.getNodeCount())
        {
            return false;       // check some basic member variables
        }
        return curEquals(this.getRoot(), check.getRoot());
    }
    private boolean curEquals(WordNode left, WordNode right)
    {
        if (left == null || right == null)      // check if the compared nodes are null
        {
            return (left == null) && (right == null);      // if either one is not null, they are not same
        }
        if (left.getValue() != right.getValue())    // check frequency
        {
            return false;
        }
        boolean check ;
        for (int i = 'a'; i <= 'z'; i++)    // check each of 26 link see if they are same
        {
            check = curEquals(left.getNodeArray()[i - 'a'],right.getNodeArray()[i - 'a']);
            if (!check)
            {
                return false;
            }
        }
        return true;
    }
    public WordNode getRoot()
    {
        return root;
    }
    public class WordNode implements ITrie.INode
    {
        private int fre;
        private WordNode[] node = new WordNode[26];
        public WordNode()
        {
            fre = 0;
            for (int i = 0; i < 26; i++)
            {
                node[i] = null;
            }
        }
        public WordNode[] getNodeArray()
        {
            return node;
        }
        public void increFre()
        {
            fre++;
        }
        public int getValue()
        {
            return fre;
        }
    }
}

