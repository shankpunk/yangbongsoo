
// FinalProjectColorDoc.cpp : CFinalProjectColorDoc Ŭ������ ����
//

#include "stdafx.h"
// SHARED_HANDLERS�� �̸� ����, ����� �׸� �� �˻� ���� ó���⸦ �����ϴ� ATL ������Ʈ���� ������ �� ������
// �ش� ������Ʈ�� ���� �ڵ带 �����ϵ��� �� �ݴϴ�.
#ifndef SHARED_HANDLERS
#include "FinalProjectColor.h"
#endif

#include "FinalProjectColorDoc.h"

#include <propkey.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CFinalProjectColorDoc

IMPLEMENT_DYNCREATE(CFinalProjectColorDoc, CDocument)

BEGIN_MESSAGE_MAP(CFinalProjectColorDoc, CDocument)
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectColorDoc::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectColorDoc::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectColorDoc::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectColorDoc::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectColorDoc::OnMedianSub)
	ON_COMMAND(IDM_MEAN_SUB, &CFinalProjectColorDoc::OnMeanSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectColorDoc::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectColorDoc::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectColorDoc::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectColorDoc::OnRotation)
END_MESSAGE_MAP()


// CFinalProjectColorDoc ����/�Ҹ�

CFinalProjectColorDoc::CFinalProjectColorDoc()
	: m_InputImageR(NULL)
	, m_InputImageG(NULL)
	, m_InputImageB(NULL)
	, m_OutputImageR(NULL)
	, m_OutputImageG(NULL)
	, m_OutputImageB(NULL)
	, m_width(0)
	, m_height(0)
	, m_Re_width(0)
	, m_Re_height(0)
{
	// TODO: ���⿡ ��ȸ�� ���� �ڵ带 �߰��մϴ�.

}

CFinalProjectColorDoc::~CFinalProjectColorDoc()
{
}

BOOL CFinalProjectColorDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: ���⿡ ���ʱ�ȭ �ڵ带 �߰��մϴ�.
	// SDI ������ �� ������ �ٽ� ����մϴ�.

	return TRUE;
}




// CFinalProjectColorDoc serialization

void CFinalProjectColorDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: ���⿡ ���� �ڵ带 �߰��մϴ�.
	}
	else
	{
		// TODO: ���⿡ �ε� �ڵ带 �߰��մϴ�.
	}
}

#ifdef SHARED_HANDLERS

// ����� �׸��� �����մϴ�.
void CFinalProjectColorDoc::OnDrawThumbnail(CDC& dc, LPRECT lprcBounds)
{
	// ������ �����͸� �׸����� �� �ڵ带 �����Ͻʽÿ�.
	dc.FillSolidRect(lprcBounds, RGB(255, 255, 255));

	CString strText = _T("TODO: implement thumbnail drawing here");
	LOGFONT lf;

	CFont* pDefaultGUIFont = CFont::FromHandle((HFONT) GetStockObject(DEFAULT_GUI_FONT));
	pDefaultGUIFont->GetLogFont(&lf);
	lf.lfHeight = 36;

	CFont fontDraw;
	fontDraw.CreateFontIndirect(&lf);

	CFont* pOldFont = dc.SelectObject(&fontDraw);
	dc.DrawText(strText, lprcBounds, DT_CENTER | DT_WORDBREAK);
	dc.SelectObject(pOldFont);
}

// �˻� ó���⸦ �����մϴ�.
void CFinalProjectColorDoc::InitializeSearchContent()
{
	CString strSearchContent;
	// ������ �����Ϳ��� �˻� �������� �����մϴ�.
	// ������ �κ��� ";"�� ���еǾ�� �մϴ�.

	// ��: strSearchContent = _T("point;rectangle;circle;ole object;");
	SetSearchContent(strSearchContent);
}

void CFinalProjectColorDoc::SetSearchContent(const CString& value)
{
	if (value.IsEmpty())
	{
		RemoveChunk(PKEY_Search_Contents.fmtid, PKEY_Search_Contents.pid);
	}
	else
	{
		CMFCFilterChunkValueImpl *pChunk = NULL;
		ATLTRY(pChunk = new CMFCFilterChunkValueImpl);
		if (pChunk != NULL)
		{
			pChunk->SetTextValue(PKEY_Search_Contents, value, CHUNK_TEXT);
			SetChunkValue(pChunk);
		}
	}
}

#endif // SHARED_HANDLERS

// CFinalProjectColorDoc ����

#ifdef _DEBUG
void CFinalProjectColorDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFinalProjectColorDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CFinalProjectColorDoc ���


BOOL CFinalProjectColorDoc::OnOpenDocument(LPCTSTR lpszPathName)
{
	if (!CDocument::OnOpenDocument(lpszPathName))
		return FALSE;

	// TODO:  ���⿡ Ư��ȭ�� �ۼ� �ڵ带 �߰��մϴ�.
	// Color �̹���ó��Ŭ����
	CImage m_bmpBitmap;

	// �̹������Ϸε�
	m_bmpBitmap.Load(lpszPathName); //Į�������� ���� 

	// ���̿������ϱ�
	m_height = m_bmpBitmap.GetHeight(); //getheight �� ���ؼ� ������ ũ�⸦ �˼� ���� 
	m_width = m_bmpBitmap.GetWidth();

	// �޸��Ҵ�.
	m_InputImageR = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageR[i] = new unsigned char [m_width];

	m_InputImageG = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageG[i] = new unsigned char [m_width];

	m_InputImageB = new unsigned char* [m_height];
	for (int i=0; i<m_height; i++)
		m_InputImageB[i] = new unsigned char [m_width];


	// Į���̹������޸𸮿��ε�
	COLORREF pixel;
	for (int i=0; i<m_height; i++)
		for(int j=0; j<m_width; j++) {
			pixel = m_bmpBitmap.GetPixel(i,j) ;

			m_InputImageR[j][i] = (unsigned char) GetRValue(pixel);
			m_InputImageG[j][i] = (unsigned char) GetGValue(pixel);
			m_InputImageB[j][i] = (unsigned char) GetBValue(pixel);
		}


		return TRUE;
}

void CFinalProjectColorDoc::OnCloseDocument()
{
	// TODO: ���⿡ Ư��ȭ�� �ڵ带 �߰� ��/�Ǵ� �⺻ Ŭ������ ȣ���մϴ�.
	int i;
	for (i=0; i<m_height; i++)
		delete m_InputImageR[i];
	delete m_InputImageR;

	for (i=0; i<m_height; i++)
		delete m_InputImageG[i];
	delete m_InputImageG;

	for (i=0; i<m_height; i++)
		delete m_InputImageB[i];
	delete m_InputImageB;

	if (m_OutputImageR != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageR[i];
		delete m_OutputImageR;
	}

	if (m_OutputImageG != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageG[i];
		delete m_OutputImageG;
	}

	if (m_OutputImageB != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImageB[i];
		delete m_OutputImageB;
	}

	CDocument::OnCloseDocument();
}

#include "ConstantDlg.h"
void CFinalProjectColorDoc::OnZoomIn()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK){
	int scale =dlg.intValue;
	m_Re_height=m_height * scale;
	m_Re_width=m_width * scale;

	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// ����̹�������ó��<�ٽɾ˰���>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			m_OutputImageR[i*scale][j*scale] = m_InputImageR[i][j];
			m_OutputImageG[i*scale][j*scale] = m_InputImageG[i][j];
			m_OutputImageB[i*scale][j*scale] = m_InputImageB[i][j];
		}
	}
	}
}


void CFinalProjectColorDoc::OnNearest()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// �޸� �Ҵ�
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		// �������̴ϱ� outputimage�� �������� 
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				m_OutputImageR[i][j] = m_InputImageR[i/scale][j/scale];
				m_OutputImageG[i][j] = m_InputImageG[i/scale][j/scale];
				m_OutputImageB[i][j] = m_InputImageB[i/scale][j/scale];
			}
		}
	}
	return;
}


void CFinalProjectColorDoc::OnBilinear()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// �޸� �Ҵ�
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];

		// ����ó�� �˰��� �ٽ� 
		double c1R,c2R,c3R,c4R; 
		double c1G,c2G,c3G,c4G; 
		double c1B,c2B,c3B,c4B; 
		double x,y; 
		double r_H, r_W; //���� �Ǽ���ġ
		int i_H, i_W;  // ���� ������ġ 
		double N; // ���� ���ϰ� ���� �� 
		double N2, N3;

		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				r_H = i / (double)scale; 
				r_W = j / (double)scale;
				i_H = (int)r_H;
				i_W = (int)r_W;
				x = r_W - i_W;
				y = r_H - i_H;
				if( i_H < 0 || i_H >= (m_height-1) ||
					i_W < 0 || i_W >= (m_width-1) ){
						m_OutputImageR[i][j] = 255;
						m_OutputImageG[i][j] = 255;
						m_OutputImageB[i][j] = 255;
				}
				else{
					c1R = m_InputImageR[i_H][i_W];
					c2R = m_InputImageR[i_H][i_W+1];
					c3R = m_InputImageR[i_H+1][i_W+1];
					c4R = m_InputImageR[i_H+1][i_W];
					////////////////////////////////
					c1G = m_InputImageG[i_H][i_W];
					c2G = m_InputImageG[i_H][i_W+1];
					c3G = m_InputImageG[i_H+1][i_W+1];
					c4G = m_InputImageG[i_H+1][i_W];
					////////////////////////////////
					c1B = m_InputImageB[i_H][i_W];
					c2B = m_InputImageB[i_H][i_W+1];
					c3B = m_InputImageB[i_H+1][i_W+1];
					c4B = m_InputImageB[i_H+1][i_W];
					////////////////////////////////
					N = c1R*(1-x)*(1-y) + c2R*x*(1-y) + c3R*x*y + c4R*(1-x)*y ;
					N2 = c1G*(1-x)*(1-y) + c2G*x*(1-y) + c3G*x*y + c4G*(1-x)*y ;
					N3 = c1B*(1-x)*(1-y) + c2B*x*(1-y) + c3B*x*y + c4B*(1-x)*y ;

					m_OutputImageR[i][j] = (unsigned char) N;
					m_OutputImageG[i][j] = (unsigned char) N2;
					m_OutputImageB[i][j] = (unsigned char) N3;
				}
			}
		}
	}
	return;
}


void CFinalProjectColorDoc::OnZoomOut()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {
	int scale =dlg.intValue;

	m_Re_height= m_height / scale;
	m_Re_width = m_width / scale;

	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// ����̹�������ó��<�ٽɾ˰���>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[i/scale][j/scale] = m_InputImageR[i][j];
			m_OutputImageG[i/scale][j/scale] = m_InputImageG[i][j];
			m_OutputImageB[i/scale][j/scale] = m_InputImageB[i][j];
		}
	}
	}
}


void CFinalProjectColorDoc::OnMedianSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// �޸� �Ҵ�
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		int mSize = scale; 
		double **Mask;
		double **Mask2;
		double **Mask3;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		Mask2 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask2[i] = new double[mSize];

		Mask3 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask3[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// ����ũ ä��� 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImageR[i+n][j+m]; 
						Mask2[n][m] = m_InputImageG[i+n][j+m]; 
						Mask3[n][m] = m_InputImageB[i+n][j+m]; 
					}
				}		

				m_OutputImageR[i/mSize][j/mSize] = OnFindMedian(Mask, mSize);
				m_OutputImageG[i/mSize][j/mSize] = OnFindMedian(Mask2, mSize);
				m_OutputImageB[i/mSize][j/mSize] = OnFindMedian(Mask3, mSize);
			}
		}
	}
	return;
}


unsigned char CFinalProjectColorDoc::OnFindMedian(double** Mask, int mSize)
{
	int i,j, index=0;
	double *Mask1;
	Mask1 = new double[mSize * mSize]; 

	//2������ -> 1�������� 
	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			Mask1[index++] = Mask[i][j];
		}
	}

	//����Ʈ���� 
	for(i=0; i<mSize * mSize; i++){
		for(j=0; j<mSize * mSize -1 ; j++){
			if(Mask1[j] > Mask1[j+1]){
				double temp = Mask1[j];
				Mask1[j] = Mask1[j+1];
				Mask1[j+1] = temp; 
			}
		}
	}

	return (unsigned char)Mask1[mSize * mSize / 2];
}


void CFinalProjectColorDoc::OnMeanSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.intValue;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// �޸� �Ҵ�
		m_OutputImageR = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageR[i] = new unsigned char [m_Re_width];

		m_OutputImageG = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageG[i] = new unsigned char [m_Re_width];

		m_OutputImageB = new unsigned char* [m_Re_height];
		for (int i=0; i<m_Re_height; i++)
			m_OutputImageB[i] = new unsigned char [m_Re_width];


		int mSize = scale; 
		double **Mask;
		double **Mask2;
		double **Mask3;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		Mask2 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask2[i] = new double[mSize];

		Mask3 = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask3[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// ����ũ ä��� 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImageR[i+n][j+m]; 
						Mask2[n][m] = m_InputImageG[i+n][j+m];
						Mask3[n][m] = m_InputImageB[i+n][j+m];
					}
				}		
				m_OutputImageR[i/mSize][j/mSize] = OnFindAVG(Mask, mSize);
				m_OutputImageG[i/mSize][j/mSize] = OnFindAVG(Mask2, mSize);
				m_OutputImageB[i/mSize][j/mSize] = OnFindAVG(Mask3, mSize);
			}
		}
	}
	return;

}


unsigned char CFinalProjectColorDoc::OnFindAVG(double** Mask, int mSize)
{
	int i,j;
	double sum = 0.0, max = 0.0;
	//double *Mask1;
	//Mask1 = new double[mSize * mSize]; 

	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			sum += Mask[i][j] ;  
		}
	}

	max = sum / (mSize *mSize) ;

	return max;
}

#include "ConstantDlg2.h"
void CFinalProjectColorDoc::OnTranslation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CConstantDlg2 dlg;
	if(dlg.DoModal() == IDOK) {
	m_Re_height= m_height;
	m_Re_width = m_width;
	
	int dx = dlg.yValue;
	int dy= dlg.xValue; 

	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// ����̹�������ó��<�ٽɾ˰���>
	int i,j;
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(i+dx >= 0 && i+dx < m_Re_height && j+dy >= 0 && j+dy < m_Re_width){
				m_OutputImageR[i+dx][j+dy] = m_InputImageR[i][j];
				m_OutputImageG[i+dx][j+dy] = m_InputImageG[i][j];
				m_OutputImageB[i+dx][j+dy] = m_InputImageB[i][j];
			}
		}
	}
	}
}


void CFinalProjectColorDoc::OnMirrorHor()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	m_Re_height= m_height;
	m_Re_width = m_width;


	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// ����̹�������ó��<�ٽɾ˰���>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[i][m_height-j-1] = m_InputImageR[i][j];
			m_OutputImageG[i][m_height-j-1] = m_InputImageG[i][j];
			m_OutputImageB[i][m_height-j-1] = m_InputImageB[i][j];

		}
	}
}


void CFinalProjectColorDoc::OnMirrorVer()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	m_Re_height= m_height;
	m_Re_width = m_width;


	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	// ����̹�������ó��<�ٽɾ˰���>
	int i,j;
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			m_OutputImageR[m_height-i-1][j] = m_InputImageR[i][j];
			m_OutputImageG[m_height-i-1][j] = m_InputImageG[i][j];
			m_OutputImageB[m_height-i-1][j] = m_InputImageB[i][j];

		}
	}
}

#include <math.h>
void CFinalProjectColorDoc::OnRotation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK){
	int angle = dlg.intValue; //���� �Է�
	double r = angle * 3.142592 / 180.0 ; //������ ���� ������ ��ȯ 
	double r90 = (90-angle) * 3.142592 / 180.0 ;
	int i,j;

	m_Re_height = m_height * cos(r) + m_width * cos(r90);
	m_Re_width = m_height * cos(r90) + m_width * cos(r);

	int x,y, xd, yd; 
	int cx = m_Re_height /2 , cy = m_Re_height /2; //������ ����

	// ����̹����޸��Ҵ�
	m_OutputImageR = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageR[i] = new unsigned char [m_Re_width];

	m_OutputImageG = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageG[i] = new unsigned char [m_Re_width];

	m_OutputImageB = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImageB[i] = new unsigned char [m_Re_width];

	for(i=0; i<m_Re_height; i++){
		for(j=0; j<m_Re_width; j++){ 
			m_OutputImageR[i][j] = 255;
			m_OutputImageG[i][j] = 255;
			m_OutputImageB[i][j] = 255;
		}
	}
	//�ӽ� �Է� �̹��� 
	double **m_TempImage1;
	double **m_TempImage2;
	double **m_TempImage3;
	m_TempImage1 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage1[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage1[i][j] = 255;

	m_TempImage2 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage2[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage2[i][j] = 255;

	m_TempImage3 = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage3[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage3[i][j] = 255;

	//�� ���� -> �ӽ� 
	int dx = (m_Re_height - m_height) /2;
	int dy = (m_Re_width - m_width) /2;

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage1[i+dx][j+dy] = m_InputImageR[i][j];
		} 
	}

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage2[i+dx][j+dy] = m_InputImageG[i][j];
		} 
	}

	for(i=0; i<m_height; i++){
		for(j=0; j<m_width; j++){
			m_TempImage3[i+dx][j+dy] = m_InputImageB[i][j];
		} 
	}

	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			x=i;
			y=j;
			xd = ((x-cx)*cos(r) + (y-cy)*sin(r)) +cx ; // �� ������ �ٽ� cx cy �������� ���� 
			yd = (- (x-cx)*sin(r) + (y-cy)*cos(r)) +cy;

			if(xd >= 0 && xd < m_Re_height && yd >= 0 && yd < m_Re_width){
				m_OutputImageR[x][y] = m_TempImage1[xd][yd];
				m_OutputImageG[x][y] = m_TempImage2[xd][yd];
				m_OutputImageB[x][y] = m_TempImage3[xd][yd];
			}
		}
	}
	}
}
